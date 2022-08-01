package e.erik.tasktimer

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import e.erik.tasktimer.databinding.ActivityMainBinding


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), AddEditFragment.OnSaveClicked {

    private lateinit var binding: ActivityMainBinding

    private var mTwoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        mTwoPane = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        var fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
        if (fragment != null) {
            // There was an existing fragment to edit a task, make sure the panes are set up correctly
            showEditPane()
        } else {
            binding.mainContent.taskDetailsContainer.visibility =
                if (mTwoPane) View.INVISIBLE else View.GONE
            binding.mainContent.mainFragment.visibility = View.VISIBLE
        }
    }

    private fun showEditPane() {
        binding.mainContent.taskDetailsContainer.visibility = View.VISIBLE
        // hide the left hand pane, if in single pane view
        binding.mainContent.mainFragment.visibility = if (mTwoPane) View.VISIBLE else View.GONE
    }

    private fun removeEditPane(fragment: Fragment? = null) {
        Log.d(TAG, "removeEditPane called")
        if (fragment != null) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        // set the visibility of the right hand pane
        binding.mainContent.taskDetailsContainer.visibility =
            if (mTwoPane) View.INVISIBLE else View.GONE
        // and show the left hand pane
        binding.mainContent.mainFragment.visibility = View.VISIBLE
    }

    override fun onSaveClicked() {
        Log.d(TAG, "onSaveClicked: called")
        var fragment = supportFragmentManager.findFragmentById(R.id.task_details_container)
        removeEditPane(fragment)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.menumain_addTask -> taskEditRequest(null)
//            R.id.menumain_settings -> true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun taskEditRequest(task: Task?) {
        Log.d(TAG, "taskEditRequest: starts")

        //Create a new fragment to edit the task
        val newFragment = AddEditFragment.newInstance(task)
        supportFragmentManager.beginTransaction()
            .replace(R.id.task_details_container, newFragment)
            .commit()

        showEditPane()
        Log.d(TAG, "Exiting takEditRequest")
    }

}