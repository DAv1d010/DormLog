package com.example.dormlog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dormlog.databinding.ItemVisitBinding
import com.example.dormlog.model.Visit
import com.example.dormlog.model.VisitStatus
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class VisitAdapter(
    private val onCheckOut: (String) -> Unit,
    private val onOpen: (String) -> Unit
) : RecyclerView.Adapter<VisitAdapter.VH>() {

    private val items = mutableListOf<Visit>()
    private val fmt = SimpleDateFormat("hh:mm a", Locale.getDefault())

    fun submit(list: List<Visit>) {
        items.clear(); items.addAll(list); notifyDataSetChanged()
    }

    inner class VH(val b: ItemVisitBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val b = ItemVisitBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return VH(b)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: VH, pos: Int) {
        val v = items[pos]
        h.b.tvVisitor.text = v.visitorName
        h.b.tvResident.text = "Visiting: ${v.residentName} · ${v.roomNumber}"
        h.b.tvStatus.text = "IN: ${fmt.format(Date(v.checkInTime))} · ${v.status.name}"

        val active = v.status == VisitStatus.ACTIVE
        h.b.btnCheckOut.visibility = if (active) android.view.View.VISIBLE
        else android.view.View.GONE

        h.b.btnCheckOut.setOnClickListener { onCheckOut(v.visitId) }
        h.b.root.setOnClickListener { onOpen(v.visitId) }
    }
}