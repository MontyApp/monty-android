package com.monty.domain

import com.google.firebase.firestore.GeoPoint
import org.threeten.bp.LocalDateTime

val description = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."

val data1 = hashMapOf(
    "title" to "Súprava BOSH - vŕtačka",
    "description" to description,
    "image" to "https://assets.bosch.com/media/global/products_and_solutions/industry_and_trade/power-tools-for-professionals_res_800x450.jpg",
    "interval" to "month",
    "deposit" to 500,
    "price" to 350,
    "categoryId" to 2,
    "address" to GeoPoint(50.08804, 14.42076),
    "currency" to "czk",
    "createdAt" to LocalDateTime.now().minusDays(1).toString(),
    "userId" to "sQy4wqzggp1HuSgM8mJF"
)

val data2 = hashMapOf(
    "title" to "Gitara Les Paul",
    "description" to description,
    "image" to "https://www.eastgatemusic.com.au/wp-content/uploads/2018/08/Gibson-Les-Paul-Classic-2018-Ebony-Electric-Guitar-in-case.jpg",
    "interval" to "week",
    "deposit" to 600,
    "price" to 500,
    "categoryId" to 2,
    "address" to GeoPoint(49.195061, 16.606836),
    "currency" to "czk",
    "createdAt" to LocalDateTime.now().minusDays(2).toString(),
    "userId" to "sQy4wqzggp1HuSgM8mJF"
)

val data3 = hashMapOf(
    "title" to "Instax Fotoaparát",
    "description" to description,
    "image" to "https://cnet1.cbsistatic.com/img/DAAL51pLvY3EWLV63iPfHG3VSGE=/1200x675/2017/09/18/00cd9dd9-7fa6-4d94-ba2b-65cdd9317eb6/fujifilm-instax-square-sq10-120.jpg",
    "interval" to "day",
    "deposit" to 200,
    "price" to 300,
    "categoryId" to 2,
    "address" to GeoPoint(50.08804, 14.42076),
    "currency" to "czk",
    "createdAt" to LocalDateTime.now().minusDays(3).toString(),
    "userId" to "sQy4wqzggp1HuSgM8mJF"
)

val data4 = hashMapOf(
    "title" to "Dron DJI Mavic",
    "description" to description,
    "image" to "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ8_xFBsUTEU2NCQOpARASEWQ1EkRkWS2aSmpVmxzLAjy0Rh-zX&s",
    "interval" to "day",
    "deposit" to 200,
    "price" to 300,
    "categoryId" to 2,
    "address" to GeoPoint(49.195061, 16.606836),
    "currency" to "czk",
    "createdAt" to LocalDateTime.now().minusDays(4).toString(),
    "userId" to "sQy4wqzggp1HuSgM8mJF"
)

val data5 = hashMapOf(
    "title" to "Kellys bicykel",
    "description" to description,
    "image" to "https://i.ytimg.com/vi/SRw-q1a4tS8/maxresdefault.jpg",
    "interval" to "day",
    "deposit" to 200,
    "price" to 300,
    "categoryId" to 1,
    "address" to GeoPoint(50.08804, 14.42076),
    "currency" to "czk",
    "createdAt" to LocalDateTime.now().minusDays(5).toString(),
    "userId" to "sQy4wqzggp1HuSgM8mJF"
)


val data6 = hashMapOf(
    "title" to "Liberta retro kolo",
    "description" to description,
    "image" to "http://images.mtbiker.sk/bazar/big/IMG_20180507_114254_5af02c482aa92.jpg",
    "interval" to "day",
    "deposit" to 300,
    "price" to 400,
    "categoryId" to 1,
    "address" to GeoPoint(49.195061, 16.606836),
    "currency" to "czk",
    "createdAt" to LocalDateTime.now().minusDays(6).toString(),
    "userId" to "sQy4wqzggp1HuSgM8mJF"
)