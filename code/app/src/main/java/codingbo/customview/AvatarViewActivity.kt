package codingbo.customview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import codingbo.customview.databinding.ActivityAvatarBinding

/**
 * Created by boliang
 * on 2022/5/15.
 */
class AvatarViewActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAvatarBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        binding.avatar.setImageResource(R.drawable.rings)
    }
}