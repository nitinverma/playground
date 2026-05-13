
MK_NAME := IPK_TOOL

# Handle Source Epoch for reproducible builds
SOURCE_DATE_EPOCH ?= $(shell date +%s)
TIMESTAMP := $(shell date --date="@$(SOURCE_DATE_EPOCH)" 2>/dev/null || date)

# Helper: $(1)=Field Name, $(2)=Directory
get_control_field = $(shell grep "^$(1):" $(2)/CONTROL/control | sed -e 's/^$(1):[[:space:]]*//')

define BuildIpkg
  # --- Namespaced Variable Assignments ---
  __$(1)_$$(MK_NAME)_DIR     := $$(strip $(2))
  __$(1)_$$(MK_NAME)_DEST    := $$(strip $(3))
  
  # Metadata extracted from the CONTROL/control file
  __$(1)_$$(MK_NAME)_VER     := $$(call get_control_field,Version,$$(__$(1)_$$(MK_NAME)_DIR))
  __$(1)_$$(MK_NAME)_ARCH    := $$(call get_control_field,Architecture,$$(__$(1)_$$(MK_NAME)_DIR))
  
  # Construction of the final file path
  __$(1)_$$(MK_NAME)_FILE    := $(1)_$$(__$(1)_$$(MK_NAME)_VER)_$$(__$(1)_$$(MK_NAME)_ARCH).ipk
  __$(1)_$$(MK_NAME)_TARGET  := $$(__$(1)_$$(MK_NAME)_DEST)/$$(__$(1)_$$(MK_NAME)_FILE)
  __$(1)_$$(MK_NAME)_TDIR    := $$(__$(1)_$$(MK_NAME)_DEST)/IPKG_BUILD.$(1)

  # --- The Build Rule ---
$$(__$(1)_$$(MK_NAME)_TARGET): $$(__$(1)_$$(MK_NAME)_DIR)
	@echo "Packaged contents of $$(__$(1)_$$(MK_NAME)_DIR) into $$@"
	@mkdir -p $$(__$(1)_$$(MK_NAME)_TDIR)
	
	# 1. Create data.tar.gz (Excluding CONTROL)
	# Flags: --format=gnu --numeric-owner --sort=name --mtime
	@tar --format=gnu --numeric-owner --sort=name --mtime="$(TIMESTAMP)" \
		-C $$(__$(1)_$$(MK_NAME)_DIR) \
		--exclude='./CONTROL' \
		-czf $$(__$(1)_$$(MK_NAME)_TDIR)/data.tar.gz .
	
	# 2. Calculate Installed-Size (uncompressed size of data.tar.gz)
	@$(eval __$(1)_$$(MK_NAME)_SIZE := $$(shell zcat < $$(__$(1)_$$(MK_NAME)_TDIR)/data.tar.gz | wc -c))
	
	# 3. Prepare CONTROL directory in tmp
	@mkdir -p $$(__$(1)_$$(MK_NAME)_TDIR)/CONTROL
	@cp -fR $$(__$(1)_$$(MK_NAME)_DIR)/CONTROL/* $$(__$(1)_$$(MK_NAME)_TDIR)/CONTROL/
	
	# 4. Update the Installed-Size in the temp control file
	@sed -i -e "s/^Installed-Size: .*/Installed-Size: $$(__$(1)_$$(MK_NAME)_SIZE)/" \
		$$(__$(1)_$$(MK_NAME)_TDIR)/CONTROL/control
	# If the field didn't exist, append it (as some ipkg variants require)
	@grep -q "Installed-Size:" $$(__$(1)_$$(MK_NAME)_TDIR)/CONTROL/control || \
		echo "Installed-Size: $$(__$(1)_$$(MK_NAME)_SIZE)" >> $$(__$(1)_$$(MK_NAME)_TDIR)/CONTROL/control

	# 5. Build control.tar.gz
	@tar --format=gnu --numeric-owner --sort=name --mtime="$(TIMESTAMP)" \
		-C $$(__$(1)_$$(MK_NAME)_TDIR)/CONTROL \
		-czf $$(__$(1)_$$(MK_NAME)_TDIR)/control.tar.gz .

	# 6. Prepare debian-binary
	@echo "2.0" > $$(__$(1)_$$(MK_NAME)_TDIR)/debian-binary

	# 7. Final Assembly (The IPK is a gzipped tar of the three files)
	@tar --format=gnu --numeric-owner --sort=name --mtime="$(TIMESTAMP)" \
		-C $$(__$(1)_$$(MK_NAME)_TDIR) \
		-cf - ./debian-binary ./data.tar.gz ./control.tar.gz | gzip -n - > $$@

	# Cleanup
	@rm -rf $$(__$(1)_$$(MK_NAME)_TDIR)

  # Track target for the 'all' rule
  IPK_ALL_TARGETS += $$(__$(1)_$$(MK_NAME)_TARGET)
endef

