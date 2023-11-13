package com.example.common.data

object DataMock {

    val locations = listOf(
        LocationMock("4th Block, HSR Layout" , 0.0 , 0.0 ) ,
        LocationMock("2nd Block, Koramangala" , 0.0 , 0.0 ) ,
        LocationMock("Electronic City" , 0.0 , 0.0 ) ,
        LocationMock("Knowledge park III" , 0.0 , 0.0 ) ,
    )

    var sLocation = locations.first().location

    val departments = listOf(
        "Design" , "IT" , "Human Resources" , "Accounting" ,
        "Risk management" , "Sport" , "International"
    )
    var sDep = departments.first()



    val users = mutableListOf(
        UserMock(
            1 , "aleyam4ndroid@gmail.com" , "123456" ,
            "k43Y6" , "Rudy" , null , false , locations.random().location , departments.random()
        ) ,
        UserMock(
            2 , "user2@gmail.com" , "212345" ,
            "2k23Y6" , "User2" , "https://picsum.photos/200?blur=6" , false , locations.random().location , departments.random()
        ) ,
        UserMock(
            3 , "user3@gmail.com" , "312345" ,
            "3k33Y6" , "User3" , null , false , locations.random().location , departments.random()
        ) ,
        UserMock(
            4 , "user4@gmail.com" , "412345" ,
            "4k43Y6" , "User4" , "https://picsum.photos/200?blur=1" , true , locations.random().location , departments.random()
        ) ,
        UserMock(
            5 , "user5@gmail.com" , "512345" ,
            "5k53Y6" , "User5" , null , true , locations.random().location , departments.random()
        ) ,
        UserMock(
            6 , "user6@gmail.com" , "612345" ,
            "6k63Y6" , "User6" , "https://picsum.photos/200?blur=2" , false , locations.random().location , departments.random()
        ) ,
        UserMock(
            7 , "user7@gmail.com" , "712345" ,
            "7k73Y6" , "User7" , null , false , locations.random().location , departments.random()
        ) ,
        UserMock(
            8 , "user8@gmail.com" , "812345" ,
            "8k83Y6" , "User8" , "https://picsum.photos/200?blur=3" , false , locations.random().location , departments.random()
        ) ,
        UserMock(
            9 , "user9@gmail.com" , "912345" ,
            "9k93Y6" , "User9" , null , true , locations.random().location , departments.random()
        ) ,
        UserMock(
            10 , "user10@gmail.com" , "101234" ,
            "10k403Y6" , "User10" , "https://picsum.photos/200?blur=4" , false , locations.random().location , departments.random()
        ) ,
        UserMock(
            11 , "user11@gmail.com" , "111234" ,
            "11k413Y6" , "User11" , null , true , locations.random().location , departments.random()
        )
    )

}

data class UserMock(val userId : Int ,
                    val email : String ,
                    val pass : String ,
                    val empId : String ,
                    val name : String ,
                    val photoUrl : String?,
                    val isConfigured : Boolean , val location : String , val dep : String)
data class LocationMock(val location : String , val lat : Double , val pass : Double)
