{Template {
  $classpath : "app.templates.DetailsLayout",
  $hasScript: true
}}

  {macro main()}
  <div id="header">
    <div class="backButton"><a {on click { fn : goHome} /}></a></div>
  </div>
  <div id="wrapper">
    <div id="scroller">
      <ul id="thelist">
        <li class="maps">
          {section {
        	id : "mapBlock",
        	macro : {
          		name: "map"
        	}
    		}/}
        </li>
        <li class="placeDetails">
          <div class="container">
            <div class="header">Place details</div>
            <div class="placeDescription">
              <div class="content"> 
		         {section {
		        	id : "placeBlock",
		        	macro : {
		          		name: "placeDetails"
		        	}
		    		}/}
               </div>
            </div>
          </div>
        </li>
        <li class="placeDetails events">
          <div class="container">
	         {section {
	        	id : "eventBlock",
	        	macro : {
	          		name: "placeEvents"
	        	}
	    		}/}
          </div>
        </li>
      </ul>
    </div>
  </div>
  <div id="footer">
    <div class="button"{on click { fn : bookme} /}>Book</div>
  </div>
  {/macro}
 {macro map()}
 <iframe  frameborder="0" scrolling="no" marginheight="0" marginwidth="0" src="https://maps.google.com/maps?f=q&amp;source=s_q&amp;hl=en&amp;geocode=&amp;q=bangalore&amp;aq=&amp;sll=37.0625,-95.677068&amp;sspn=63.640894,135.263672&amp;ie=UTF8&amp;hq=&amp;hnear=Bangalore,+Bangalore+Urban,+Karnataka,+India&amp;ll=12.971599,77.594563&amp;spn=0.624407,1.056747&amp;t=m&amp;z=11&amp;output=embed"></iframe>
 {/macro}
 {macro placeDetails()}
 	<img src="img/place.png"> 
 		<span><strong>Place:</strong> Bangalore, India</span> 
 		<span><strong>Description:</strong> Bangalore is the capital city of the Indian state of Karnataka. Located on the Deccan Plateau in the south-eastern part of Karnataka. 
                Bangalore is India's third most populous city and fifth-most populous urban agglomeration. Bangalore is known as the Silicon Valley of India 
                because of its position as the nation's leading Information technology (IT) exporter.[6][7][8] Located at a height of over 3,000 feet (914.4 m) above sea level, </span> \
  {/macro}
 {macro placeEvents()}
    <div class="header">Events in Bangalore</div>
    <div class="placeDescription">
      <div class="content"> 
      	<img src="img/04.jpg"> 
      	<span><strong class="name">Oktoberfest</strong> </span> 
      	<span class="des">Oktoberfests have long been a part of German festivity. 
      	The Royal Orchid offers its own version of the celebration at its English pub, Geoffrey's. 
      	Geoffrey's at The Royal Orchid is transforming itself to provide a lively dining experience, 
      	in order to celebrate the historic Oktoberfest. The a la carte menu, specially designed for the festival, 
      	has a wide range of sizzlers that feature German favourites like sauerkraut, spaetzle, bratwurst and frikadelle will be served. 
      	Both vegetarian and non-vegetarian options will be available. 
      	Considering beer is a big part of the festival, the pub will serve a wide range of beer brands for guests to explore. 
      	These include Kingfisher, Fosters, Budweiser, Tuborg, Heineken, Carlsberg, Peroni, Corona, Christoffel, Orval and St Bernadus. 
      	</span> 
      </div>
    </div>
 {/macro}
 

{/Template}