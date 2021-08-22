package br.com.knowledge.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.knowledge.data.model.Article
import br.com.knowledge.databinding.ItemArticleBinding

class ArticlesListAdapter : ListAdapter<Article, ArticlesListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: ItemArticleBinding,
    ) :  RecyclerView.ViewHolder(binding.root){
        fun bind(item: Article) {
            binding.titleArticle.text = item.name
            binding.descriptionArticle.text = item.description
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Article, newItem: Article) = oldItem.id == newItem.id
}