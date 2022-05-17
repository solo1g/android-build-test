# Build instructions

- `./gradlew assembleDebug` builds the debug apk which can be found in `app/build/outputs/apk/debug`

---

### Changes done

- Use `minSdkVersion` 26. Some required methods (`MethodHandle.invoice` and `MethodHandle.invokeExact`) were only supported from this version.

```
Task :app:mergeExtDexDebug FAILED
ERROR:/home/zero/.gradle/caches/modules-2/files-2.1/org.scala-lang/scala-library/2.13.8/5a865f03a794b27e6491740c4c419a19e4511a3d/scala-library-2.13.8.jar: D8: com.android.tools.r8.a: MethodHandle.invoke and MethodHandle.invokeExact are only supported starting with Android O (--min-api 26)
```

- Use scala 2.13 jars only. Android does not have `java.lang.ClassValue` which we use and a fallback for it to support systems like android was added with scala 2.13.7 (https://github.com/scala/scala/releases)

- Add `packagingOptions` to remove unncecessarily added files. Some dependecies had jars filled with images, html pages etc which conflicted each other (eg index.html present in multiple jars). By default android merges all that but requires there to be no conflict.\
  Not fully sure that any unused file is not added. Just removed the minimum to get it to work.\
  IIRC `scodec-bits` dependency adds a bunch of images and fonts in `lib` folder of built apk. Android uses this folder for any native binaries that may be required (\*) and having anything else there leads to `INSTALL_FAILED_NO_MATCHING_ABIS`. Mentioning this here separately as the error message does not in any way indicate the source of the problem.

- `libsecp256k1` which relies on native code does not work out of the box. The `org.scijava.nativelib` loader we use cannot detect it directly. The device I am testing on uses `aarch64` so I tried `linux_arm64` binaries which led to `libc.so.6` apparently, you can't use libraries built for normal linux on android as `libc.so.6` is a shared runtime available on linux but not on Android ([here](https://stackoverflow.com/questions/37592218/android-ndk-and-dynamic-load-library-with-dlopen)). Only (\*) option would be to use `NDK` to link against the Android C runtime library.

---

\* = not 100% sure
