{Template {
  $classpath : "app.templates.DetailsLayout",
  $hasScript: true,
  $dependencies: ['aria.utils.Number']
}}

  {macro main()}
	<div class="container" id="details">
	  <div id="header">
		<div class="backButton"><a href="index.html"></a></div>
	  </div>
	  <div id="wrapper">
		<div id="scroller">
		  <ul id="thelist">
			<li class="maps" id="map-canvas">
			</li>
			<li class="placeDetails">
			  <div class="container">
				<div class="header">Place details</div>
				<div class="placeDescription">
				  <div class="content"> <img src="img/place.png"> <span><strong>Place:</strong> Bangalore, India</span> </span><strong>Description:</strong> Bangalore is the capital city of the Indian state of Karnataka. Located on the Deccan Plateau in the south-eastern part of Karnataka. 
					Bangalore is India's third most populous city and fifth-most populous urban agglomeration. Bangalore is known as the Silicon Valley of India 
					because of its position as the nation's leading Information technology (IT) exporter.[6][7][8] Located at a height of over 3,000 feet (914.4 m) above sea level, </span> 
				  </div>
				</div>
			  </div>
			</li>
			<li class="placeDetails events">
			  <div class="tabs"><span class="selected" id="packages" {on tap {fn:'packageClicked'}/}>Packages</span><span id="events" {on tap {fn:'eventClicked'}/}>Events</span></div>
			  <div class="container" id="packageList">
				<div class="freeText">Packages dates from next month <span class="changeDate">Change dates</span> </div>
				
				// set the flight and hotel
				{if this.data.packages 
					&& this.data.packages.flight && this.data.packages.hotels}
					
					{var flight = this.data.packages.flight/}
					{foreach pkg in this.data.packages.hotels}
						<div class="placeDescription packages">
						  <div class="content placeDes">
							<img src="${pkg.htImg}">
							<div class="packagetitle">${pkg.htNm}</div>
							<div class="PackageDescription"><span class="days1">${pkg.ar}</span></div>
							<table border="0" class="flightInfo">
							  <tr>
								<td class="departure">${flight.al}</td>
								<td class="airline">
									<img src="${flight.img}"/>
								</td>
								<td>${flight.airline_name}</td>
							  </tr>
							  <tr>
								<td class="arrival">${flight.al}</td>
								<td class="airline"><img src="${flight.img}"/></td>
								<td>${flight.airline_name}</td>
							  </tr>
							</table>
							<div class="price">
								{var orgPrice = pkg.pf + pkg.ps/}
								<span class="strikeOut">Rs.${aria.utils.Number.formatCurrency(orgPrice, '#.##')}</span>
								<span class="mainPrice">Rs.${aria.utils.Number.formatCurrency(pkg.pf, '#.##')}</span>
								<span class="saving">Save Rs.${aria.utils.Number.formatCurrency(pkg.ps, '#.##')}</span>
							</div>
							<span class="bookButton" {on tap {fn: 'showPopup', args: aria.utils.Number.formatCurrency(pkg.pf, '#.##')}/}>Book</span> 
						  </div>
						</div>
					{/foreach}
				{else/}
					<div class="errorMsg">Oops!!! No packages found.</div>
				{/if}
			  </div>
			  <div class="container" id="eventslist">
				{if this.data.events}
					{foreach event in this.data.events}
						<div class="placeDescription">
							<div class="content"> <img src="img/Neil-Island2_81x64.jpg"> <span><strong class="name">Event 1</strong> </span> <span class="des">Oktoberfests have long been a part of German festivity. The Royal Orchid offers its own version of the celebration at its English pub, Geoffrey's. Geoffrey's at The Royal Orchid is transforming itself to provide a lively dining experience, in order to celebrate the historic Oktoberfest. The a la carte menu, specially designed for the festival, has a wide range of sizzlers that feature German favourites like sauerkraut, spaetzle, bratwurst and frikadelle will be served. Both vegetarian and non-vegetarian options will be available. Considering beer is a big part of the festival, the pub will serve a wide range of beer brands for guests to explore. These include Kingfisher, Fosters, Budweiser, Tuborg, Heineken, Carlsberg, Peroni, Corona, Christoffel, Orval and St Bernadus. </span> </div>
						</div>
					{/foreach}
				{else/}
					<div class="errorMsg">Sorry!!! There are no events.</div>
				{/if}
			  </div>
			</li>
		  </ul>
		</div>
	  </div>
	  <div id="footer">
		<div class="button">Book</div>
	  </div>
	</div>
	<div class="mask"></div>
	<div class="popup2">
	  <div>
		<div class="subHeader">More options <span class="close3" {on tap {fn: 'hidePopup'}/}></span></div>
		<ul class="upsell">
		  <li class="title">Winter Special - Sikkim Darjeeling Delight</li>
		  <li class="offers">Between Jan 2014  to  Feb 2015 <span class="currency" id="popCurrency">INR 21000</span><span class="bookDeal">Proceed</span></li>
		  <li class="options subHeader1">More deals</li>
		  <li class="offers bB">Between Feb 2014  to  Mar 2015 <span class="currency">INR 20000</span><span class="bookDeal">Book this trip</span></li>
		  <li class="offers">Between Mar 2014  to  Apr 2015 <span class="currency">INR 19000</span><span class="bookDeal">Book this trip</span></li>
		</ul>
	  </div>
	</div>
  {/macro}

{/Template}