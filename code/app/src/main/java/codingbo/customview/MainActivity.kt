package codingbo.customview

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import codingbo.customview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    fun unread1(view: View?) = start(UnreadViewActivity::class.java)

    fun avatarView(view: View?) = start(AvatarViewActivity::class.java)

    private fun start(clazz: Class<out AppCompatActivity>) {
        startActivity(Intent(this, clazz))
    }
}