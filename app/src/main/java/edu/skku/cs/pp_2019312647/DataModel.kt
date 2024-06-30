package edu.skku.cs.pp_2019312647

import android.provider.ContactsContract.CommonDataKinds.Nickname
import android.webkit.WebStorage.Origin
import android.widget.RatingBar

data class Map(
        var city : String ?= null,
        var name : String ?= null,
        var jibun_address : String ?= null,
        var road_address : String ?= null,
        var latitude  : String ?= null  ,
        var longitude :   String ?= null,
        var number    : String ?= null )

data class WhiskeyCell(
        var id  : Int ?= null,
        var name : String ?= null,
        var price : String ?= null,
        var origin : String ?= null,
        var link : String ?= null,
        var alcohol : String ?= null,
        var description : String ?= null)

data class ReviewCell(
        var nickname: String ?= null,
        var gender : String ?= null,
        var age_group : String ?= null,
        var comment : String ?= null,
        var rating : Float ?=null,

)


// 장소명, 주소, 좌표만 받는 클래스

/*
data class Place(
        var place_name: String, // 장소명, 업체명
        var address_name: String, // 전체 지번 주소
        var road_address_name: String, // 전체 도로명 주소
        var x: String, // X 좌표값 혹은 longitude
        var y: String, // Y 좌표값 혹은 latitude
)*/


data class PlaceSearchResult(
        val candidates: List<Candidate>
)

data class Candidate(
        val geometry: Geometry
)

data class Geometry(
        val location: Location
)

data class Location(
        val lat: Double,
        val lng: Double
)
data class PlacesNearbySearchResult(
        val results: List<Result>
)

data class Result(
        val geometry: Geometry,
        val name: String
)




data class GeoResultItem(
        val geometry: GeoResultGeometry
)

data class GeoResultGeometry(
        val location: GeoResultLocation
)

data class GeoResultLocation(
        val lat: Double,
        val lng: Double
)

