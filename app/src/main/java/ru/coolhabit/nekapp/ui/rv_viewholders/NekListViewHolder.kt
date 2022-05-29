package ru.coolhabit.nekapp.ui.rv_viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.coolhabit.nekapp.data.Nek
import ru.coolhabit.nekapp.databinding.NekItemBinding

class NekListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val nekItemBinding = NekItemBinding.bind(itemView)
    //Привязываем view из layout к переменным
    private val nekName = nekItemBinding.nekName
    private val nekDate = nekItemBinding.nekDate
    private val nekCount = nekItemBinding.nekPhotoCount


    //В этом методе кладем данные из cast в наши view
    fun bind(nek: Nek) {
        //Устанавливаем заголовок
        nekName.text = nek.title
        nekDate.text = nek.date
        nekCount.text = nek.imageList.size.toString()
    }
}