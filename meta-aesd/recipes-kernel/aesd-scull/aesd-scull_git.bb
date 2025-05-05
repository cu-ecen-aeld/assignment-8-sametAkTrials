SUMMARY = "AESD LDD3 Scull Module Recipe"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

inherit module

SRC_URI = " git://git@github.com/cu-ecen-aeld/assignment-7-sametAkTrials.git;protocol=ssh;branch=master \
            file://scull-makefile.patch;patchdir=../ \
            file://scull-load.patch;patchdir=../ \
            file://S98scullmodule \
          "

PV = "1.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git/scull"

EXTRA_OEMAKE += "KERNELDIR='${STAGING_KERNEL_DIR}'"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES:${PN} += "kernel-module-scull"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S98scullmodule"

do_install:append () {
	install -d ${D}/etc/
	install -m 0755 ${S}/scull_load ${D}/etc/
    install -m 0755 ${S}/scull_unload ${D}/etc/

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/S98scullmodule ${D}${sysconfdir}/init.d/
}

FILES:${PN} += "/etc/*"
FILES:${PN} += "${sysconfdir}/init.d/S98scullmodule"
