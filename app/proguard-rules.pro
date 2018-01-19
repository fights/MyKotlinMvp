# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# bugly混淆
#-dontwarn com.tencent.bugly.**
#-keep public class com.tencent.bugly.**{*;}

#高斯模糊混淆
#-keep class android.support.v8.renderscript.** { *; }

#Glide
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep public class * extends com.bumptech.glide.module.AppGlideModule
#-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
#}
#
## for DexGuard only
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

#视频播放
#-keep class tv.danmaku.ijk.** { *; }
#-dontwarn tv.danmaku.ijk.**
#-keep class com.shuyu.gsyvideoplayer.** { *; }
#-dontwarn com.shuyu.gsyvideoplayer.**
