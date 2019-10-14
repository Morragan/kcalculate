package com.example.dietapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dietapp.DietApp
import com.example.dietapp.R
import javax.inject.Inject


class HomeActivity : AppCompatActivity(), HomeView {

    @Inject
    lateinit var presenter: HomePresenter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val activityComponent = (application as DietApp).appComponent.newActivityComponent()
        activityComponent.inject(this)

        // TODO: skeletonize

        presenter.getMealEntries()

//        btn_scan.setOnClickListener {
//            IntentIntegrator(this).initiateScan()
//        }
    }

    override fun onStart() {
        super.onStart()
        presenter.bind(this)
    }

    override fun onStop() {
        presenter.unbind()
        super.onStop()
    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        val result: IntentResult? =
//            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
//
//        if (result != null) {
//            if (result.contents != null)
//                Toast.makeText(this, "Scanned" + result.contents, Toast.LENGTH_LONG).show()
//            else
//                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
//        } else
//            super.onActivityResult(requestCode, resultCode, data)
//    }
}
