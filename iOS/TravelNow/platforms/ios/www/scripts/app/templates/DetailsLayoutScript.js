Aria.tplScriptDefinition({
    $classpath : "app.templates.DetailsLayoutScript",
	$constructor: function(){
		var myScroll;
	},    
	$prototype : {
		$displayReady: function(){
						 
			myScroll = new iScroll('wrapper');
						 
			 var cityData = JSON.parse(window.localStorage.getItem("clickedCityDetails"));
			 var eventsData = JSON.parse(window.localStorage.getItem(cityData.cityName));

			document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
			document.addEventListener('DOMContentLoaded', function () { setTimeout(loaded, 200); }, false);
		},
						 
		 $dataReady: function(){
		 //JSON Data
		 var cityData = JSON.parse(window.localStorage.getItem("clickedCityDetails"));
		 var eventsData = JSON.parse(window.localStorage.getItem(cityData.cityName));
		 
		 var placeDetailsJson = {};
		 placeDetailsJson.cityDetail = {};
		 
		 placeDetailsJson.cityDetail.name = eventsData.name;
		 placeDetailsJson.cityDetail.description = cityData.desc;
		 placeDetailsJson.cityDetail.imageID = cityData.imageID;
		 
		 placeDetailsJson.events = eventsData;
		 this.data = placeDetailsJson;
		 },

		goHome: function(e){
			e.preventDefault();
			pageEngine.navigate({"pageId": "HOME"});
		},
		bookme: function(){
			alert("coming soon");
		},
		dummyData: function(){
			var myData = {};
//			myData
		}
	}
})