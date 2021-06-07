import android.os.Bundle

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.chalet.ChaletViewModel
import com.example.programming_mobile_project.database.ChaletDB
import com.example.programming_mobile_project.databinding.ChaletFragmentBinding
import com.example.programming_mobile_project.models.Chalet
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

class ChaletFragment : Fragment() {
    val viewModel: ChaletViewModel by viewModels()
    val args: ChaletFragmentArgs by navArgs()
    private lateinit var binding: ChaletFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.chalet_fragment, container, false)
        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.chalet_fragment)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .load(R.drawable.loading)
            .into(binding.immagineChalet)

        val chaletId = args.chaletId
        val chaletDB = ChaletDB()
        chaletDB.getChalet(chaletId)
        val chaletObserver = Observer<Chalet> { chalet ->
            Glide.with(this)
                .load(chalet.locandina)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(binding.immagineChalet)
            binding.nomeChalet.text = chalet.nome_chalet
            binding.indirizzoChalet.text = chalet.indirizzo
            binding.descrizioneChalet.text = chalet.descrizione
            binding.totLettini.text = chalet.tot_lettini.toString()
            binding.totOmbrelloni.text = chalet.tot_ombrelloni.toString()
            binding.totSdraio.text = chalet.tot_sdraio.toString()
            binding.totSedie.text = chalet.tot_sedie.toString()
        }
        chaletDB.selectedChalet.observe(viewLifecycleOwner, chaletObserver)
    }

}