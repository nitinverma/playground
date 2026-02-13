# macOS Catalina 10.15.7 – A1425 Installer Archive

**Target machine:** MacBook Pro A1425 (2012–2013 Retina)  
**macOS version:** Catalina 10.15.7  
**Purpose:** Offline, future-proof macOS installer created via `createinstallmedia`  
**Archive date:** 2026-01-28

---

## Contents

```
macOS_Catalina_10.15.7_A1425_createinstallmedia.cdr.xz
macOS_Catalina_10.15.7_A1425_createinstallmedia.sha256.txt
README.md
```

---

## How this archive was created

### 1. Create bootable installer USB (Apple-supported method)

```bash
sudo /Applications/Install\ macOS\ Catalina.app/Contents/Resources/createinstallmedia   --volume /Volumes/InstallUSB
```

This produces a bootable HFS+ macOS installer USB.

---

### 2. Identify and unmount the USB (do NOT eject)

```bash
diskutil list
diskutil unmountDisk /dev/diskX
```

`diskX` refers to the entire USB device (not a partition).

---

### 3. Create a raw, bit-for-bit disk image

```bash
sudo dd if=/dev/rdiskX of=macos-catalina-a1425.raw bs=8m
```

**Notes:**
- Uses `/dev/rdiskX` for raw (faster) access
- Captures the entire device, including partition map and free space
- Image size reflects full USB capacity (expected and correct)

---

### 4. Convert raw image to Apple UDIF (`.cdr`)

```bash
hdiutil convert macos-catalina-a1425.raw -format UDTO -o macos-catalina-a1425
```

Result:
```
macos-catalina-a1425.cdr
```

---

### 5. Generate checksum

```bash
shasum -a 256 macos-catalina-a1425.cdr   > macOS_Catalina_10.15.7_A1425_createinstallmedia.sha256.txt
```

---

### 6. Compress for long-term storage

```bash
xz -T0 -9 macos-catalina-a1425.cdr
```

Final archive:
```
macOS_Catalina_10.15.7_A1425_createinstallmedia.cdr.xz
```

---

## How to restore the installer USB

### 1. Decompress

```bash
xz -d macOS_Catalina_10.15.7_A1425_createinstallmedia.cdr.xz
```

---

### 2. Verify checksum (optional but recommended)

```bash
shasum -a 256 -c macOS_Catalina_10.15.7_A1425_createinstallmedia.sha256.txt
```

---

### 3. Write image back to USB  
(USB capacity must be ≥ image size)

```bash
diskutil list
diskutil unmountDisk /dev/diskX
sudo dd if=macOS_Catalina_10.15.7_A1425_createinstallmedia.cdr         of=/dev/rdiskX bs=8m
sync
diskutil eject /dev/diskX
```

---

## How to inspect / mount the image (without restoring)

```bash
hdiutil attach macOS_Catalina_10.15.7_A1425_createinstallmedia.cdr
```

This mounts:
```
/Volumes/Install macOS Catalina
```

---

## Notes & Caveats

### `dd` block size on macOS
macOS uses BSD `dd`, not GNU `dd`.

- Use lowercase suffixes:
  ```bash
  bs=8m
  ```
- Uppercase `M` is GNU syntax and may not behave consistently on macOS.

---

### About image size
- Image size equals full USB capacity — this is intentional
- macOS installer media preallocates filesystem space
- This ensures a faithful, bit-for-bit recovery

---

### About `asr imagescan`
- `asr imagescan` is not applicable to raw `createinstallmedia` USB clones
- Failure from `asr imagescan` does not indicate corruption
- Restoring via `dd` is the correct and supported method

---

## Restore method summary

- Archive format: Apple UDIF (`.cdr`)
- Compression: `xz`
- Integrity: SHA256
- Restore tool: `dd`
- Independent of Apple Internet Recovery or servers

---

## End
