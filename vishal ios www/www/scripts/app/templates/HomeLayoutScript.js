Aria.tplScriptDefinition({
    $classpath: "app.templates.HomeLayoutScript",
    $constructor: function () {
        this.data = {};
    },
    $prototype: {
        $dataReady: function () {
            this.data.age = 4;
            this.data.cats = cats;
        },

        $displayReady: function () {
            var that = this;

            $('.skin-square input').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
                increaseArea: '20%'
            });

            $(".mask, .popUp, .newPopup, .popUp1").hide();
            $("#mCondition, label[for='mCondition']").click(function () {
                if ($('#mCondition').prop('checked')) {
                    $(".mask, .popUp1").show();
                }

            });

            $(".close2, .done").click(function () {
                $(".mask, .popUp1").hide();
            });
            $(".close").click(function () {
                $(".mask, .popUp").hide();
            })

        },
        changeAge: function () {
            this.data.age = $("#age").val();
            this.$refresh({
                outputSection: "catlist"
            });
        },

        //
        getDetails: function () {
            var myData = this.collectData();
            if (myData) {
                this.getPlaceInformation(myData);
            }
        },

        //getPlaceInformation
        getPlaceInformation: function (criteriaData) {
            $("#loading, .mask").show();

            var tripType = criteriaData.tripType;

            var regionName = "";
            if (tripType == "domestic") {
                //TO GET THE GEO LOCATION COUNTRY
                regionName = "&region=India";
            }


            var categoryList = criteriaData.catlist;
            var keywordData = categoryList.join(" ");

            var medList = criteriaData.medlist;
            var medListData = medList.join(" ");

            var that = this;
            //EventBrite
            var url = "http://1.hackathon-amadeus.appspot.com/GetTopEvents?date=Future&keywords=" 
				+ keywordData 
				+ " " 
				+ medListData 
				+ regionName 
				+ "&max_cities=3&max_events=5&lat=" 
				+ window.localStorage.getItem("gpsLat") 
				+ "&lng=" + window.localStorage.getItem("gpsLng")

            $.ajax({
                url: url,
                cache: false,
                type: "POST",
                processData: false,
                dataType: "json",
                crossDomain: "true",
                complete: function (responseData, textStatus, jqXHR) {

                    if (textStatus == "success") {
                        //Response data
                        var resp = JSON.parse(responseData.responseText);
						
						if (json == null || json.home == null) {
							json = {home: {}};
						}
						
						// get city details from JSON
						json.home.gpsCityName = resp.city.name;
						json.home.gpsCityCode = resp.city.code;

                        //Name of the cities
                        var cityValues = new Array();
                        cityValues = Object.keys(resp.deals);

                        try {
                            //1st City
                            var cityName = cityValues[0];
                            var regionName = resp.deals[cityName].events[0].region_name;
                            var countryName = resp.deals[cityName].events[0].country_name;

                            var keyCityName = cityName.replace(" ", "_");
                            keyCityName = keyCityName.toLowerCase();
                            resp.deals[cityName].name = cityName;
							
                            // storage
							json.home.city0 = JSON.stringify(resp.deals[cityName]);
							json.home[keyCityName] = JSON.stringify(resp.deals[cityName]);

                            var myData = {};
							
                            //TODO: change text
                            myData.title = "Inspired by friends";
                            myData.desc = "The top three locations where several of your Facebook friends have recently enjoyed visiting," + "and we're offering you great deals and offers to try it yourself";

                            myData.list = new Array();

                            myData.list[0] = {};
                            myData.list[0].title = cityName;
                            myData.list[0].key = keyCityName;
                            myData.list[0].desc = regionName + ", " + countryName;

                            //2nd City
                            cityName = cityValues[1];
                            regionName = resp.deals[cityName].events[0].region_name;
                            countryName = resp.deals[cityName].events[0].country_name;

                            keyCityName = cityName.replace(" ", "_");
                            keyCityName = keyCityName.toLowerCase();
                            resp.deals[cityName].name = cityName;
							
							json.home[keyCityName] = JSON.stringify(resp.deals[cityName]);
							json.home.city1 = JSON.stringify(resp.deals[cityName].airport);
							
                            myData.list[1] = {};
                            myData.list[1].title = cityName;
                            myData.list[1].key = keyCityName;
                            myData.list[1].desc = regionName + ", " + countryName;

                            //3rd City
                            cityName = cityValues[2];
                            regionName = resp.deals[cityName].events[0].region_name;
                            countryName = resp.deals[cityName].events[0].country_name;

                            keyCityName = cityName.replace(" ", "_");
                            keyCityName = keyCityName.toLowerCase();
                            resp.deals[cityName].name = cityName;
							
							json.home[keyCityName] = JSON.stringify(resp.deals[cityName]);
							json.home.city2 = JSON.stringify(resp.deals[cityName].airport);

                            myData.list[2] = {};
                            myData.list[2].title = cityName;
                            myData.list[2].key = keyCityName;
                            myData.list[2].desc = regionName + ", " + countryName;

                        } catch (e) {
                            console.log("Error: " + e.message);
                        }

                        //Call DisplayPopUp
                        that.displayPlaceListPopUp(myData);
                    } else {
                        console.log("FAILED IF");
                    }
                },
                error: function (responseData, textStatus, errorThrown) {
                    console.log('POST Failed');
                }
            });


        },

        //Display PLACES from SEASONAL
        displayPlaceListPopUp: function (seasonalData) {
			
			// initialize if null
			if (json == null || json.home == null) {
				json = {home: {}};
			}
			
			// set data for later reference
			json.home.result = seasonalData;
			
			// refresh popup
            this.$refresh({
                outputSection: "popuplist"
            });
			
            var btn = $(".button");
            var that = this
            btn.click(function () {

                var clickedCity = this.getAttribute("data");

                clickedCity = clickedCity.toLowerCase();
                clickedCity = clickedCity.replace(" ", "_");

                that.getPlaceDetail(clickedCity);
            })

            $("#loading, .mask").show();
            $("#loading").hide();
            $(".popUp, .dialog").show();

        },


        //Get City Description + Image
        getPlaceDetail: function (cityName) {

            var that = this;
            $(".popUp, .dialog").hide();
            $("#loading, .mask").show();

            //City Description
            var cityDescriptionUrl = "https://www.googleapis.com/freebase/v1/text/en/" + cityName;

            $.ajax({
                url: cityDescriptionUrl,
                cache: true,
                type: "GET",
                processData: false,
                dataType: "json",
                crossDomain: "true",
                complete: function (responseData, textStatus, jqXHR) {

                    if (textStatus == "success") {
                        var data = JSON.parse(responseData.responseText);

                        var cityDescription = data.result;

                        //City Image URL
                        var imageIDUrl = "https://www.googleapis.com/freebase/v1/topic/en/" + cityName + "?filter=/image&limit=1";

                        $.ajax({
                            url: imageIDUrl,
                            cache: true,
                            type: "GET",
                            processData: false,
                            dataType: "json",
                            crossDomain: "true",
                            complete: function (responseData, textStatus, jqXHR) {

                                if (textStatus == "success") {
                                    var imageData = JSON.parse(responseData.responseText);
                                    var cityImageID = imageData.id;

                                    //City information for DETAILS page
                                    var cityData = {};
                                    cityData.cityName = cityName;
                                    cityData.desc = cityDescription;
                                    cityData.imageID = cityImageID;
									
									// initialize if NULL
									if (json.details == null) {
										json.details = {};
									}
									
									// get clicked city details
									json.details.clickedCityDetails = cityData;

                                    $("#loading, .mask").hide();

                                    //Navigation data for TPL
                                    pageEngine.navigate({
                                        "pageId": "DETAILS"
                                    })

                                }
                            },
                            error: function (responseData, textStatus, errorThrown) {
                                console.log('FAILEDS API CALL');
                            }
                        });
                    }
                },
                error: function (responseData, textStatus, errorThrown) {
                    console.log('POST failed.');
                }

            });
        },

        //Get Keywords + Criteria + Medical Stats + Place of travel
        collectData: function () {

            var that = this;
            var myData = {};
            if ($('#domestic').prop('checked')) {
                myData.tripType = "domestic";
            } else {
                myData.tripType = "international";
            }
            myData.catlist = new Array();
            if ($("input.catlist:checked").length) {

                $("input.catlist:checked").each(function (index) {
                    var i = $(this).prop("id") - 1;
                    myData.catlist[index] = that.data.cats.lists[i].keyword;
                });
                myData.medlist = new Array();
                if ($('#mCondition').prop('checked')) {
                    $("input.medlist:checked").each(function (index) {
                        myData.medlist[index] = $(this).val();
                    });
                }
            } else {
                alert("Please Select Travel Preference...!");
                myData = false;
            }

            return myData;
        }
    }
})