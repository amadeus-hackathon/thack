Aria.tplScriptDefinition({
    $classpath : "app.templates.HomeLayoutScript",
	$constructor: function(){
		this.data = {};
	},    
	$prototype : {
		$dataReady: function() {
			this.data.age = 4;
			this.data.cats = cats;
		},
		
		$displayReady: function(){

            $('.skin-square input').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
                increaseArea: '20%'
              });

			$(".mask, .popUp, .newPopup, .popUp1").hide();
			$("#mCondition, label[for='mCondition']").click(function(){
				if($('#mCondition').prop('checked')) {
					$(".mask, .popUp1").show();
				}
				
			});
						
			$(".close2, .done").click(function(){
				$(".mask, .popUp1").hide();
			});
		},
		changeAge: function(){
			this.data.age = $("#age").val();
			this.$refresh({
			    outputSection : "catlist"
			});
		},
		getDetails: function(){
			var myData = this.collectData();
			console.log(myData);
			this.data.home = myData;
			pageEngine.navigate({"pageId": "DETAILS"})
		},
		collectData: function(){
			var that = this;
			var myData = {};
			if($('#domestic').prop('checked')) {
				myData.tripType = "domestic";
			} else {
				myData.tripType = "international";				
			}
			myData.catlist = new Array();
			$( "input.catlist:checked" ).each(function( index ) {
				var i = $( this ).prop("id")-1;
				myData.catlist[index] = that.data.cats.lists[i].title;
			});
			myData.medlist = new Array();
			if($('#mCondition').prop('checked')) {
				$( "input.medlist:checked" ).each(function( index ) {
					myData.medlist[index] = $( this ).val();
				});
			} 
			return myData;
		}
	}
})