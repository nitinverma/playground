MKFILE_PATH := $(abspath $(lastword $(MAKEFILE_LIST)))
MKFILE_DIR := $(dir $(MKFILE_PATH))
APP_VERSION=1.0

# ---- Auto Discover Binaries ----
CMD_DIR=cmd
GO_MAIN_FILES := $(wildcard $(CMD_DIR)/*/main.go)
PKG_DIRS := $(patsubst $(CMD_DIR)/%/main.go,%,$(GO_MAIN_FILES))

# ---- Router ----
include router.mk

# ---- IPK Tool ----
# include ipkg.mk

# ---- Platform ----
GOOS=linux
GOARCH=mipsle
GOMIPS=softfloat
CGO_ENABLED=0
PKG_ARCH=mipsel_24kc

# ---- Build ----
BUILD_DIR=build
TARGET_FILES := $(patsubst %,$(BUILD_DIR)/%_$(APP_VERSION)_$(PKG_ARCH).ipk,$(PKG_DIRS))
IPK_DIR=$(BUILD_DIR)/ipk
LDFLAGS=-s -w -buildid=
BUILD_FLAGS=-trimpath -ldflags="$(LDFLAGS)"

# --- Tools --
# 1. Detect OS
UNAME_S := $(shell uname -s)

# 2. Dynamic Tool Mapping
ifeq ($(UNAME_S),Darwin)
    # macOS: Look for Homebrew GNU versions (usually prefixed with 'g')
    TAR  := $(shell command -v gtar || command -v tar)
    SED  := $(shell command -v gsed || command -v sed)
    GREP := $(shell command -v ggrep || command -v grep)
    AR   := $(shell command -v gar || command -v ar)
else
    # Linux: Default tools are already GNU
    TAR  := tar
    SED  := sed
    GREP := grep
    AR   := ar
endif

test:
	@echo "Using TAR:  $(TAR)"
	@echo "Using SED:  $(SED)"
	@echo "Using GREP:  $(GREP)"
	@echo "Using AR:  $(AR)"
	@echo "Version:"
	$(TAR) --version | head -n 1

do:
	@echo ${PKG_DIRS}

$(BUILD_DIR):
	@mkdir -p $(BUILD_DIR)

$(IPK_DIR): | $(BUILD_DIR)
	@mkdir -p $(IPK_DIR)

$(eval AA := $(shell ls | wc -l))

## include ipkg.mk

define GEN_TARGET
__$(1)_GO_BINARY := $(BUILD_DIR)/$(1)-$$(GOOS)-$$(GOARCH)
$(BUILD_DIR)/$(1): | $(BUILD_DIR)
	@echo "Building: $(1) targets file $$@ output $$(__$(1)_GO_BINARY)"
	GOOS=$$(GOOS) \
	GOARCH=$$(GOARCH) \
	GOMIPS=$$(GOMIPS) \
	CGO_ENABLED=$$(CGO_ENABLED) \
	go build $$(BUILD_FLAGS) -o $$(__$(1)_GO_BINARY) ./$$(CMD_DIR)/$(1);
	ln -sf $$(abspath $$(MKFILE_DIR)/$$(__$(1)_GO_BINARY)) $$@
	@ls -al $$(__$(1)_GO_BINARY)
	@file $$(__$(1)_GO_BINARY)



$$(IPK_DIR)/$(1): | $$(IPK_DIR) $(BUILD_DIR)/$(1)
	@mkdir -p $$@
	@mkdir -p $$@/CONTROL
	# Create control file
	echo "Package: $(1)" > $$@/CONTROL/control
	echo "Version: $(APP_VERSION)" >> $$@/CONTROL/control
	echo "Section: utils" >> $$@/CONTROL/control
	echo "Priority: optional" >> $$@/CONTROL/control
	echo "Architecture: $(PKG_ARCH)" >> $$@/CONTROL/control
	echo "Maintainer: Nitin" >> $$@/CONTROL/control
	echo "Description: Auto-built Go binary $(1)" >> $$@/CONTROL/control

	@mkdir -p $$@/usr/bin
	@cp $$(__$(1)_GO_BINARY) $$@/usr/bin

$$(BUILD_DIR)/.ipk/$(1): | $$(IPK_DIR)/$(1)
	@mkdir -p $$@
	# 1. Create data.tar.gz
	$$(TAR) --format=gnu --numeric-owner --sort=name -C $$(IPK_DIR)/$(1) --exclude='./CONTROL' -czf $$@/data.tar.gz .
	file $$@/data.tar.gz
	stat $$@/data.tar.gz
	# 2. Prepare CONTROL
	@mkdir -p $$@/CONTROL
	@cp -fR $$(IPK_DIR)/$(1)/CONTROL/* $$@/CONTROL
	$(eval S := $$(shell stat -c "%s" *$$(BUILD_DIR)/.ipk/$(1)/data.tar.gz 2>/dev/null))
	echo "Installed-Size: $(S)" >> $$@/CONTROL/control;
	$$(TAR) --format=gnu --numeric-owner --sort=name -C $$(IPK_DIR)/$(1)/CONTROL -czf $$@/control.tar.gz .
	# 3. Final Assembly
	@echo "2.0" > $$@/debian-binary

$$(BUILD_DIR)/$(1)_$(APP_VERSION)_$(PKG_ARCH).ipk: | $$(BUILD_DIR)/.ipk/$(1)
	@echo $$@
	( cd $$(BUILD_DIR)/.ipk/$(1) && tar --format=gnu --numeric-owner --sort=name -cf - ./debian-binary ./data.tar.gz ./control.tar.gz | gzip -n - > ../../$(1)_1.0_$(PKG_ARCH).ipk)

IPK_ALL_TARGETS += $$($$(BUILD_DIR)/$(1)_$(APP_VERSION)_$(PKG_ARCH).ipk)

endef

$(foreach dir,$(PKG_DIRS),$(eval $(call GEN_TARGET,$(dir))))

all: $(TARGET_FILES)

# ---- Clean ----
clean:
	@rm -rf $(BUILD_DIR)

.PHONY: all build clean

