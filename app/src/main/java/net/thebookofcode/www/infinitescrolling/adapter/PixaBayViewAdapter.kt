package net.thebookofcode.www.infinitescrolling.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.thebookofcode.www.infinitescrolling.databinding.ListItemLayoutBinding
import net.thebookofcode.www.infinitescrolling.model.Photo

class PixaBayViewAdapter():PagingDataAdapter<Photo, PixaBayViewAdapter.ViewHolder>(PHOTO_COMPARATOR) {

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean =
                oldItem == newItem
        }
    }

    inner class ViewHolder(private val itemBinding: ListItemLayoutBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun bind(photo: Photo) = with(itemBinding){
            Glide.with(itemView.context)
                .load(photo.previewURL)
                .into(mediaImage)
            primaryText.text = photo.userName
            subText.text = photo.likes.toString()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { photo ->
            holder.bind(photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(itemBinding)
    }
}