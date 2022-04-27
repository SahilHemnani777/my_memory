package com.example.mymemory

import BoardSize
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min

class ImagePickerAdapter(private val context: Context,
                         private var imageUris: List<Uri>,
                         private val boardSize: BoardSize) :
    RecyclerView.Adapter<ImagePickerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_card,  parent, false)
        val imageViewLayoutParams = view.findViewById<ImageView>(R.id.ivCustomImage).layoutParams
        val cardHeight = parent.height / boardSize.getHeight()
        val cardWidth = parent.width / boardSize.getWidth()
        val cardSideLength = min(cardHeight, cardWidth)
        imageViewLayoutParams.width = cardSideLength
        imageViewLayoutParams.height = cardSideLength
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // given a particular position, how are we gonna display UI
        if(position < imageUris.size){
            holder.bind(imageUris[position])
        }else{
            holder.bind()
        }
    }

    override fun getItemCount(): Int {
         return boardSize.getNumPairs()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivCusttomImage = itemView.findViewById<ImageView>(R.id.ivCustomImage)

        fun bind(uri: Uri) {
            ivCusttomImage.setImageURI(uri)
            ivCusttomImage.setOnClickListener(null)
        }

        fun bind() {
            ivCusttomImage.setOnClickListener {
                // Launch an intent to select the photo
            }
        }
    }

}
