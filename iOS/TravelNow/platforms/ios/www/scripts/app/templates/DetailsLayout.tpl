{Template {
  $classpath : "app.templates.DetailsLayout",
  $hasScript: true
}}

  {macro main()}
  <div id="header">
    <div class="backButton"><a href="index.html"></a></div>
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
 	<img src="https://usercontent.googleapis.com/freebase/v1/image${data.cityDetail.imageID}">
 		<span><strong>Place:</strong> ${data.cityDetail.name}</span>
 		<span><strong>Description:</strong> ${data.cityDetail.description}</span>
  {/macro}
 {macro placeEvents()}
    <div class="header">Events in ${data.cityDetail.name}</div>
	    {foreach eventData in data.events.events}

    <div class="placeDescription">
      <div class="content"> 
      	<img src="${eventData.image}">
      	<span><strong class="name">${eventData.event_name}</strong> </span>
      	<span class="des">${decodeURIComponent(eventData.description).replace("+", " ")}</span>
      </div>
	      {/foreach}

    </div>
 {/macro}
 

{/Template}