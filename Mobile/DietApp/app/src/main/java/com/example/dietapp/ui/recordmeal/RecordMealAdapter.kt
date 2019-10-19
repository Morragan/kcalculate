package com.example.dietapp.ui.recordmeal

import android.content.Context
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton
import com.example.dietapp.R
import com.example.dietapp.models.MealDTO
import com.example.dietapp.models.RecordMealDTO
import com.google.android.material.button.MaterialButton
import java.util.*

class RecordMealAdapter(
    private val addMealOnClickListener: AddMealOnClickListener,
    private val context: Context
) :
    RecyclerView.Adapter<RecordMealAdapter.ViewHolder>(), Filterable {

    private val meals = mutableListOf<MealDTO>()
    private val filteredMeals = mutableListOf<MealDTO>()

    private var expandedPosition = -1
    lateinit var recyclerView: RecyclerView

    fun replaceMeals(meals: List<MealDTO>) {
        this.meals.clear()
        filteredMeals.clear()
        this.meals.addAll(meals)
        filteredMeals.addAll(meals)
        notifyDataSetChanged()
    }

    //
    override fun getFilter() = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<MealDTO>()
            if (constraint.isNullOrBlank())
                filteredList.addAll(meals)
            else {
                val pattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()
                for (item: MealDTO in meals) {
                    if (item.name.toLowerCase(Locale.getDefault()).contains(pattern))
                        filteredList.add(item)
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            filteredMeals.clear()
            @Suppress("UNCHECKED_CAST")
            filteredMeals.addAll(results?.values as Collection<MealDTO>)
            this@RecordMealAdapter.notifyDataSetChanged()

        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredMeals.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val meal = filteredMeals[position]

        holder.addMealButton.setOnClickListener {
            // validate
            val mealWeight = holder.weight.text.toString().toIntOrNull()
            if (mealWeight == null || mealWeight <= 0) {
                holder.weight.error = context.getString(R.string.error_meal_weight_input)
                return@setOnClickListener
            }

            val mealEntry =
                RecordMealDTO(meal.name, meal.nutrients, holder.weight.text.toString().toInt())
            addMealOnClickListener.onAddMealClick(mealEntry)
            holder.addMealButton.startAnimation()
        }

        val isExpanded = position == expandedPosition
        holder.details.visibility = if(isExpanded) View.VISIBLE else View.GONE
        holder.itemView.isActivated = isExpanded
        holder.title.setOnClickListener {
            expandedPosition = if (isExpanded) -1 else position
            TransitionManager.beginDelayedTransition(recyclerView)
            notifyDataSetChanged()
        }

        holder.title.text = meal.name
        holder.carbs.text = meal.nutrients.carbsGram.toString()
        holder.fats.text = meal.nutrients.fatGram.toString()
        holder.protein.text = meal.nutrients.proteinGram.toString()
        holder.kcalPer100.text = meal.nutrients.kcalPer100Gram.toString()
    }


    class ViewHolder(rootItemView: View) :
        RecyclerView.ViewHolder(rootItemView) {

        internal val addMealButton =
            rootItemView.findViewById<CircularProgressButton>(R.id.record_meal_list_item_button_add_meal)
        internal val title = rootItemView.findViewById<TextView>(R.id.record_meal_list_item_title)
        internal val details = rootItemView.findViewById<ConstraintLayout>(R.id.record_meal_list_item_details)
        internal val carbs = rootItemView.findViewById<TextView>(R.id.record_meal_list_item_carbs)
        internal val fats = rootItemView.findViewById<TextView>(R.id.record_meal_list_item_fats)
        internal val protein =
            rootItemView.findViewById<TextView>(R.id.record_meal_list_item_protein)
        internal val kcalPer100 =
            rootItemView.findViewById<TextView>(R.id.record_meal_list_item_kcalper100)
        internal val weight =
            rootItemView.findViewById<EditText>(R.id.record_meal_list_item_input_meal_weight)
    }

    interface AddMealOnClickListener {
        fun onAddMealClick(meal: RecordMealDTO)
    }
}