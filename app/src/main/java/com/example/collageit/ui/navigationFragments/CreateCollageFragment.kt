package com.example.collageit.ui.navigationFragments

import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.collageit.ImageUploadActivity
import com.example.collageit.collageCreation.ChooseCollageFormatActivity
import com.example.collageit.databinding.FragmentCreateCollageBinding


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateCollageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateCollageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentCreateCollageBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentCreateCollageBinding.inflate(inflater, container, false)

        binding.buttonCreateCollageUpload.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.type = "image/*"
            startActivityForResult(intent, ImageUploadActivity.PHOTO_REQUEST)
//            startActivity(Intent(activity, ImageUploadActivity::class.java))

        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ImageUploadActivity.PHOTO_REQUEST) if (resultCode == AppCompatActivity.RESULT_OK) {
            Log.d(ImageUploadActivity.TAG, "onActivityResult: uri - ${data?.clipData}")
            val clipData = data?.clipData!!

            // TODO: Only accept valid file extensions
            val fileUris = (0 until clipData.itemCount).map { clipData.getItemAt(it).uri }

            // TODO: Send URIS to ChooseCollageFormatActivity
            val photoUriList = arrayListOf<Uri>()
            for (uri in fileUris) {
                photoUriList.add(uri)
            }
            Log.d(ImageUploadActivity.TAG, "onActivityResult: photoUriList - $photoUriList")
            /***************************
             *  NOTE TO JUSTIN         *
             *  -------------          *
             *                         *
             *  INSERT EXTRAS HERE     *
             *                         *
             ***************************/

//            for (uri in fileUris) {
//                ImageUploadActivity.upload(requireContext(), uri)
//            }
            val intent = Intent(context, ChooseCollageFormatActivity::class.java)
            intent.putExtra(ChooseCollageFormatActivity.PASSED_IMAGES_EXTRA, photoUriList)
            startActivity(intent)

        }
    }

    private fun getPath(uri: Uri): String {
        val contentResolver = requireActivity().applicationContext.contentResolver
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = contentResolver.query(uri, projection, null, null, null)!!
        Log.d(ImageUploadActivity.TAG, "getPath: cursor Not Null")
        var filePath = ""
        cursor.let {
            if (it.moveToFirst()) {
                Log.d(ImageUploadActivity.TAG, "getPath: moved to first")
                val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                Log.d(
                    ImageUploadActivity.TAG,
                    "getPath: ${cursor.isBeforeFirst || cursor.isAfterLast}"
                )
                filePath = cursor.getString(columnIndex)!!
                Log.d(ImageUploadActivity.TAG, "getPath: filepath not null")
            }
            cursor.close()
        }
        return filePath
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreateCollageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateCollageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}