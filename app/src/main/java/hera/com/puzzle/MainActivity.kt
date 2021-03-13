package hera.com.puzzle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hera.com.puzzle.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivityViewModel : ViewModel() {
    private val colors = arrayOf(
            R.color.purple_700, R.color.purple_200, R.color.purple_500,
            R.color.queen_blue, R.color.cadet_blue, R.color.zomp,
            R.color.pistachio, R.color.maize_crayola, R.color.mango_tango,
            R.color.yellow_orange_wheel, R.color.red_salsa, R.color.white,
            R.color.black,  R.color.teal_700, R.color.cg_blue,
            R.color.teal_200
    )

    val boxColors = Array(10) { colors[Random.nextInt(colors.size)] }

    fun changeColor(i: Int) {
        var newColor: Int
        do {
            newColor = colors[Random.nextInt(colors.size)]
        } while (boxColors[i] == newColor)
        boxColors[i] = newColor
    }

    fun checkEquality(): Boolean {
        val first = boxColors[0]
        for(i in boxColors) {
            if (first != i) return false
        }
        return true
    }

    fun setPrizeImage() = when(boxColors[0]) {
        colors[0] -> R.drawable.pic_11
        colors[1] -> R.drawable.pic_13
        colors[2] -> R.drawable.pic_12
        colors[3] -> R.drawable.pic_4
        colors[4] -> R.drawable.pic_14
        colors[5] -> R.drawable.pic_15
        colors[6] -> R.drawable.pic_16
        colors[7] -> R.drawable.pic_1
        colors[8] -> R.drawable.pic_3
        colors[9] -> R.drawable.pic_2
        colors[10] -> R.drawable.pic_6
        colors[11] -> R.drawable.pic_10
        colors[12] -> R.drawable.pic_5
        colors[13] -> R.drawable.pic_9
        colors[14] -> R.drawable.pic_8
        else -> R.drawable.pic_7
    }

}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val boxViews = arrayOf(
                binding.box1, binding.box2, binding.box3,
                binding.box4, binding.box5, binding.box6,
                binding.box7, binding.box8, binding.box9,
                binding.box10
        )

        setColorBoxes(viewModel, boxViews)
        setOnClick(viewModel, boxViews)
        if (viewModel.checkEquality()) setPrize(viewModel)

    }

    private fun setColorBoxes(viewModel: MainActivityViewModel, boxViews: Array<TextView>) {
        for (i in boxViews.indices) boxViews[i].setBackgroundResource(viewModel.boxColors[i])
    }

    private fun setOnClick(viewModel: MainActivityViewModel, boxViews: Array<TextView>) {
        for (i in boxViews.indices) boxViews[i].setOnClickListener {
            setColor(viewModel, boxViews[i], i)
        }
    }

    private fun setColor(viewModel: MainActivityViewModel, box: TextView, i: Int) {
        viewModel.changeColor(i)
        box.setBackgroundResource(viewModel.boxColors[i])
        if (viewModel.checkEquality()) setPrize(viewModel)
    }

    private fun setPrize(viewModel: MainActivityViewModel) {
        binding.layout1.visibility = View.GONE
        binding.layout2.visibility = View.GONE
        binding.layout3.visibility = View.GONE
        binding.layout4.visibility = View.GONE
        binding.prizeImage.setImageResource(viewModel.setPrizeImage())
        binding.prizeImage.visibility = View.VISIBLE
    }

}