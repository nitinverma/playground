### Makefile ###
.PHONY: package compile clean
.DEFAULT_GOAL := compile

TARGET := target
GEN_SRC := $(TARGET)/gen_src
BINARY := $(TARGET)/bin
TARS := $(TARGET)/tars
PKG_DIR := pkg
files := $(shell seq -f$(GEN_SRC)/nitin.%g 1 200)

compile: $(BINARY) $(BINARY)/nitin

$(TARS)/%.tar.gz: $(TARGET)/% 
	tar -zcvf $@ $^

$(BINARY)/nitin: $(files)
	cat -n $^ > $@

$(GEN_SRC)/nitin.% : $(GEN_SRC)
	echo "$@" > $@

$(GEN_SRC):
	mkdir -p $@

$(BINARY):
	mkdir -p $@

$(TARS):
	mkdir -p $@

package: compile $(TARS) $(TARS)/gen_src.tar.gz $(TARS)/bin.tar.gz

yinst_create: compile
	cd $(PKG_DIR) && $(MAKE) nightly

clean: clean_yinst_create
	rm -rf $(TARGET)

clean_yinst_create:
	cd $(PKG_DIR) && $(MAKE) clean

