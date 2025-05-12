SUMMARY = "AESD Char Driver Recipe"
DESCRIPTION = "${SUMMARY}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=8ed1a118f474eea5e159b560c339329b"

inherit module

SRC_URI = " git://git@github.com/cu-ecen-aeld/assignments-3-and-later-sametAkTrials.git;protocol=ssh;branch=master \
            file://S98aesdchardriver \
          "

PV = "1.0+git${SRCPV}"
SRCREV = "${AUTOREV}"

S = "${WORKDIR}/git/aesd-char-driver"

EXTRA_OEMAKE += "KERNELDIR='${STAGING_KERNEL_DIR}'"

# The inherit of module.bbclass will automatically name module packages with
# "kernel-module-" prefix as required by the oe-core build environment.

RPROVIDES:${PN} += "kernel-module-aesd-char-driver"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "S98aesdchardriver"

do_install:append () {
	install -d ${D}/etc/
	install -m 0755 ${S}/aesdchar_load ${D}/etc/
    install -m 0755 ${S}/aesdchar_unload ${D}/etc/

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/S98aesdchardriver ${D}${sysconfdir}/init.d/
}

FILES:${PN} += "/etc/*"
FILES:${PN} += "${sysconfdir}/init.d/S98aesdchardriver"
