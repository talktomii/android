package com.furniture.utlis

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.furniture.R
import com.furniture.utlis.listner.PermissionCallback
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


object ImageUtils {
    const val REQ_CODE_CAMERA_PICTURE = 1
    const val REQ_CODE_GALLERY_PICTURE = 2
    const val REQ_CODE_GALLERY_VIDEO = 21
    const val REQ_CODE_CAMERA_VIDEO = 212
    private val TAG = ImageUtils::class.java.simpleName
    private var imageFile: File? = null
    private var videoFile: File? = null
/*

    fun displayImagePicker(parentContext: Any) {
        var context: Context? = null
        if (parentContext is Fragment) {
            context = parentContext.context
        } else if (parentContext is Activity)
            context = parentContext

        if (context != null) {
            val pickerItems = arrayOf(
                context.getString(R.string.camera),
                context.getString(R.string.choose_gallery),
                context.getString(android.R.string.cancel)
            )

            val builder = android.app.AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.select_your_choice))
            builder.setItems(pickerItems) { dialog, which ->
                when (which) {
                    0 -> openCamera(parentContext)

                    1 -> openGallery(parentContext)
                }
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
*/


    fun displayImagePicker(parentContext: Any, listner: () -> Unit) {
        var context: Context? = null
        if (parentContext is Fragment) {
            context = parentContext.context
        } else if (parentContext is Activity)
            context = parentContext

        if (context != null) {
            val pickerItems = arrayOf(
                context.getString(R.string.camera),
                context.getString(R.string.choose_gallery),
//                context.getString(R.string.gif),
                context.getString(android.R.string.cancel)
            )

            val builder = android.app.AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.select_your_choice))
            builder.setItems(pickerItems) { dialog, which ->
                when (which) {
                    0 -> openCamera(parentContext)

                    1 -> openGallery(parentContext)

                    2 -> listner.invoke()
                }
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    fun displayImagePicker(parentContext: Any?, frggManager: FragmentManager) {
        var context: Context? = null
        if (parentContext is Fragment) {
            context = parentContext.context
        } else if (parentContext is Activity) context = parentContext
        if (context != null) {
            val pickerItems = arrayOf(context.getString(R.string.camera), context.getString(R.string.gallery), context.getString(android.R.string.cancel))
            val bottomsheet = OpenCameraVideoFragment(object : CameraGalleryCallback {
                override fun cameraClick() {
                    context.cameraPermission(object : PermissionCallback {
                        override fun permissionGranted() {
                            openCamera(parentContext)
                        }

                        override fun permissionRejected() {

                        }

                    })
                }

                override fun galleryClick() {
                    context.galleryclick(object : PermissionCallback {
                        override fun permissionGranted() {
                            openGallery(parentContext);
                        }

                        override fun permissionRejected() {
                        }

                    })

                }

            }, context.getString(R.string.camera), context.getString(R.string.gallery))

            bottomsheet.show(frggManager, "")
        }
    }
/*

    fun displayCameraPicker(parentContext: Any) {
        var context: Context? = null
        if (parentContext is Fragment) {
            context = parentContext.context
        } else if (parentContext is Activity) context = parentContext
        if (context != null) {
            val pickerItems = arrayOf(
                context.getString(R.string.dialog_camera),
                context.getString(android.R.string.cancel)
            )
            val builder =
                AlertDialog.Builder(context)
            builder.setTitle(context.getString(R.string.dialog_select_your_choice))
            builder.setItems(
                pickerItems
            ) { dialog: DialogInterface, which: Int ->
                when (which) {
                    0 -> openCamera(parentContext)
                }
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
*/

    /**
     * Open the device camera using the ACTION_IMAGE_CAPTURE intent
     *
     * @param uiReference Reference of the current ui.
     * Use either android.support.v7.app.AppCompatActivity or android.support
     * .v4.app.Fragment
     * //     * @param imageFile   Destination image file
     */

    private fun openCamera(uiReference: Any?) {
        var context: Context? = null
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            if (uiReference is Fragment) context =
                uiReference.context else if (uiReference is AppCompatActivity) context = uiReference
            if (context != null) {

                if (context.externalCacheDir != null) imageFile =
                    createImageFile(context.externalCacheDir!!.path)
                // Put the uri of the image file as intent extra
                val imageUri = imageFile?.let {
                    FileProvider.getUriForFile(
                        context,
                        /*BuildConfig.APPLICATION_ID + */"com.furniture.provider",
                        it
                    )
                }
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                // Get a list of all the camera apps
                val resolvedIntentActivities = context.packageManager.queryIntentActivities(
                    cameraIntent,
                    PackageManager.MATCH_DEFAULT_ONLY
                )
                // Grant uri read/write permissions to the camera apps
                for (resolvedIntentInfo in resolvedIntentActivities) {
                    val packageName = resolvedIntentInfo.activityInfo.packageName
                    context.grantUriPermission(
                        packageName,
                        imageUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                }
                if (uiReference is Fragment)
                    (uiReference).startActivityForResult(cameraIntent, REQ_CODE_CAMERA_PICTURE)
                else (uiReference as AppCompatActivity).startActivityForResult(
                    cameraIntent,
                    REQ_CODE_CAMERA_PICTURE
                )
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun openGallery(uiReference: Any?) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (uiReference is Fragment) {
            uiReference.startActivityForResult(
                galleryIntent,
                REQ_CODE_GALLERY_PICTURE
            )
        } else if (uiReference is AppCompatActivity)
            uiReference.startActivityForResult(
                galleryIntent,
                REQ_CODE_GALLERY_PICTURE
            )
    }

    fun openVideoGallery(uiReference: Any?) {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        galleryIntent.type = "video/*"
        if (uiReference is AppCompatActivity) uiReference.startActivityForResult(
            galleryIntent,
            REQ_CODE_GALLERY_VIDEO
        ) else if (uiReference is Fragment) uiReference.startActivityForResult(
            galleryIntent,
            REQ_CODE_GALLERY_VIDEO
        )


    }

    fun getImagePathFromGallery(
        context: Context,
        imageUri: Uri
    ): File? {
        var imagePath: String? = null
        val filePathColumn =
            arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(
            imageUri,
            filePathColumn, null, null, null
        )
        if (cursor != null) {
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            imagePath = cursor.getString(columnIndex)
            cursor.close()
        }
        return saveImageToExternalStorage(context, getFile(imagePath))
    }

    fun getVideoPathFromGallery(
        context: Context,
        imageUri: Uri
    ): File? {
        var imagePath: String? = null
        val filePathColumn =
            arrayOf(MediaStore.Video.Media.DATA)
        val cursor = context.contentResolver.query(
            imageUri,
            filePathColumn, null, null, null
        )
        if (cursor != null) {
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            imagePath = cursor.getString(columnIndex)
            cursor.close()
        }
        return File(imagePath)
    }

    private fun saveImageToExternalStorage(
        context: Context,
        finalBitmap: Bitmap?
    ): File? {
        val file2: File
        val mediaStorageDir = File(context.externalCacheDir!!.path)
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null
            }
        }
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(Date())
        file2 = File(
            mediaStorageDir.path + File.separator
                    + "IMG_" + timeStamp + ".jpg"
        )
        try {
            val out: FileOutputStream = FileOutputStream(file2)
            finalBitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
            return file2
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return file2
    }

    private fun getFile(imgPath: String?): Bitmap? {
        var bMapRotate: Bitmap? = null
        try {
            if (imgPath != null) {
                val exif = ExifInterface(imgPath)
                val mOrientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 1
                )
                val options = BitmapFactory.Options()
                options.inJustDecodeBounds = true
                BitmapFactory.decodeFile(imgPath, options)
                options.inSampleSize = calculateInSampleSize(options, 400, 400)
                options.inJustDecodeBounds = false
                bMapRotate = BitmapFactory.decodeFile(imgPath, options)
                if (mOrientation == 6) {
                    val matrix = Matrix()
                    matrix.postRotate(90f)
                    bMapRotate = Bitmap.createBitmap(
                        bMapRotate, 0, 0,
                        bMapRotate.width, bMapRotate.height,
                        matrix, true
                    )
                } else if (mOrientation == 8) {
                    val matrix = Matrix()
                    matrix.postRotate(270f)
                    bMapRotate = Bitmap.createBitmap(
                        bMapRotate, 0, 0,
                        bMapRotate.width, bMapRotate.height,
                        matrix, true
                    )
                } else if (mOrientation == 3) {
                    val matrix = Matrix()
                    matrix.postRotate(180f)
                    bMapRotate = Bitmap.createBitmap(
                        bMapRotate, 0, 0,
                        bMapRotate.width, bMapRotate.height,
                        matrix, true
                    )
                }
            }
        } catch (e: OutOfMemoryError) {
            bMapRotate = null
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            bMapRotate = null
            e.printStackTrace()
        }
        return bMapRotate
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int, reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize > reqHeight
                && halfWidth / inSampleSize > reqWidth
            ) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    /*  @NonNull
    public static String getFacebookProfileImage(@NonNull String profileId)
    {
        return "https://graph.facebook.com/" + profileId + "/picture?type=large";
    }*/
/*private void cropAndRotateImage(Fragment fragment, File sourceFile, File destinationFile)
    {
        Context context = fragment.getContext();
        if (sourceFile != null && destinationFile != null)
        {
            Uri sourceFileUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID +
            ".provider",
                    sourceFile);

            Uri destinationFileUri = FileProvider.getUriForFile(context, BuildConfig
            .APPLICATION_ID + ".provider",
                    destinationFile);

            CropImage.activity(sourceFileUri)
                    .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setAspectRatio(1, 1)
                    .setMinCropResultSize(700, 700)
                    .setRequestedSize(2000, 2000)
                    .setOutputCompressQuality(90)
                    .setOutputUri(destinationFileUri)
                    .start(context, fragment);
        } else
            Log.e(TAG, "Source or destination file is null");
    }*/

    @Throws(IOException::class)
    private fun createImageFile(directory: String): File? {
        var imageFile: File? = null
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            val storageDir = File(directory)
            if (!storageDir.mkdirs()) {
                if (!storageDir.exists()) {
                    Log.d(TAG, "Failed to create directory")
                    return null
                }
            }
            val imageFileName = "JPEG_" + System.currentTimeMillis() + "_"
            imageFile = File.createTempFile(imageFileName, ".jpg", storageDir)
        }
        return imageFile
    }

    @Throws(IOException::class)
    private fun createVideoFile(directory: String): File? {
        var imageFile: File? = null
        if (Environment.MEDIA_MOUNTED == Environment
                .getExternalStorageState()
        ) {
            val storageDir = File(directory)
            if (!storageDir.mkdirs()) {
                if (!storageDir.exists()) {
                    Log.d(TAG, "Failed to create directory")
                    return null
                }
            }
            val imageFileName = "VIDEO" + System.currentTimeMillis() + "_"
            imageFile = File.createTempFile(imageFileName, ".mp4", storageDir)
        }
        return imageFile
    }

    /**
     * Revoke access permission for the content URI to the specified package otherwise the
     * permission won't be
     * revoked until the device restarts.
     */
/* public static void revokeUriPermission(Context context, File file)
    {
        Log.d(TAG, "Uri permission revoked");
        context.revokeUriPermission(FileProvider.getUriForFile(context,
                BuildConfig.APPLICATION_ID + ".provider", file),
                Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
    }*/
    fun getFile(context: Context): File? {
        return if (imageFile != null) saveImageToExternalStorage(
            context,
            getFile(imageFile?.path)
        ) else null
    }

    fun getVideoFile(context: Context): File? {
        return if (videoFile != null) saveImageToExternalStorage(
            context,
            getFile(videoFile?.path)
        ) else null
    }
}