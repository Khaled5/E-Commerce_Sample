package com.easyinc.normalrepository.ui

import android.location.Geocoder
import android.os.Bundle
import android.view.View
import com.easyinc.mappractice.common.Logger
import com.easyinc.normalrepository.R
import com.easyinc.normalrepository.base.fragment.BaseFragment
import com.easyinc.normalrepository.data.model.WarehouseEntity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.fragment_warehouse.*
import kotlinx.android.synthetic.main.warehouse_toolbar.*
import java.util.*


class WarehouseFragment : BaseFragment(), OnMapReadyCallback {

    var mMap: GoogleMap? = null

    private lateinit var warehouse: WarehouseEntity

    override fun layoutId(): Int {
        return R.layout.fragment_warehouse
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        warehouse = requireArguments().getParcelable("warehouse")!!


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showProgressBar()

        warehouse_backpress.setOnClickListener {
            activity?.onBackPressed()
        }

        if (!warehouse.id.equals("")) {
            initMap()
            drawData()
        }
    }

    private fun drawData() {
       val rate = if (warehouse.rate.isEmpty() || warehouse.rate.isBlank()) "0.0" else warehouse.rate
       requestManager.load(warehouse.image).into(warehouse_image)
       warehouse_name.text = warehouse.name
       warehouse_about.text = warehouse.about
       warehouse_rating.rating = warehouse.rate.toFloat()
       warehouse_address.text = getWarehouseAddress()

    }

    private fun initMap(){
        val fragment =
            childFragmentManager.findFragmentById(R.id.warehouse_map) as SupportMapFragment?
        fragment?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap

        animCamera(LatLng(warehouse.lat,warehouse.lang))
    }

    private fun animCamera(latLang: LatLng, zoom: Float = 12f){

        mMap?.clear()
        val marker = MarkerOptions().position(latLang)
        marker.draggable(true)
        val locationMarker = mMap?.addMarker(marker.title(getWarehouseAddress()))
        locationMarker?.showInfoWindow()
        mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latLang.latitude,latLang.longitude), zoom))
        hideProgressBar()
    }

    private fun getWarehouseAddress(): String{
        val geoCoder = Geocoder(context, Locale.getDefault())
        val addresses = geoCoder.getFromLocation(warehouse.lat,warehouse.lang,1)
        val address = addresses[0].getAddressLine(0)
        val city = addresses[0].locality
        val state = addresses[0].adminArea

        return "$address / $city / $state"
    }


    private fun showProgressBar(){
        warehouse_progress.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        warehouse_progress.visibility = View.GONE
    }


}