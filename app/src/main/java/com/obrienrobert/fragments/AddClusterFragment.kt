package com.obrienrobert.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.afollestad.vvalidator.field.FieldValue
import com.afollestad.vvalidator.form
import com.fkorotkov.kubernetes.newCluster
import com.obrienrobert.main.R
import com.obrienrobert.models.ClusterModel
import kotlinx.android.synthetic.main.add_cluster.*
import me.nikhilchaudhari.asynkio.core.get
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.collections.forEachWithIndex
import org.jetbrains.anko.info


class AddClusterFragment : Fragment(), AnkoLogger{

    lateinit var optionsMenu: Menu

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Required to access the menu items
        return inflater.inflate(R.layout.add_cluster, container, false)
    }

    //enable options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    //inflate the menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_cluster_menu, menu)
        optionsMenu = menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_cluster_menu_item -> {
                info { "Add new cluster button clicked!" }
                validateNewClusterForm(optionsMenu, item)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validateNewClusterForm(menu: Menu, item: MenuItem){
        info { "validating new cluster" }
        val clusterForm = form {
            input(R.id.cluster_url) {
                isNotEmpty()
                /*
                   isUrl()*
                   contains("http")

                 */
            }
            input(R.id.username) {
                     isNotEmpty()
                /*
                         length().greaterThan(6)
                         length().lessThan(16)*/
            }
            input(R.id.password) {
                isNotEmpty()
                /*
                length().greaterThan(6)
                length().lessThan(20)*/
            }

            }.submitWith(menu, item.itemId) { result ->

            val clusterURL: FieldValue<*>? = result["cluster_url"]
            val username: FieldValue<*>? = result["username"]
            val password: FieldValue<*>? = result["password"]

            val cluster = ClusterModel("", clusterURL?.asString(), username?.asString(), password?.asString())
            info {cluster.toString()
            }
        }
    }

    companion object {
        fun newInstance(): AddClusterFragment {
            return AddClusterFragment()
        }
    }
}
