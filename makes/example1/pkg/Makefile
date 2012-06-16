
.DEFAULT_GOAL := nightly
.PHONY: nightly, clean, timestamp

define pkg_create
	yinst_create -buildtype nightly $(1).yicf -c
endef


all: startime nightly endtime

startime endtime: 
	date +%s%N

nightly:
	$(call pkg_create, example1)

clean: 
	rm -rf example1-*
