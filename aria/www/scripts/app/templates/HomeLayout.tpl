{Template {
  $classpath : "app.templates.HomeLayout",
  $hasScript: true
}}

  {macro main()}
  <div id="wrapper">
    <div id="scroller">
      <ul id="thelist">
        <li class="img">
          <div class="images"><span class="title">amadeus | <span class="titleName">Travel Now</span></span></div>
        </li>
        <li class="headerTxt"> Inspirations </li>
        <li>
        	<span class="facebook" {on click { fn : facebookData} /}>Friends</span>
        	<span class="seasonal" {on click { fn : sessData} /}>Seasonal</span>
        	<span class="wellBeing" {on click { fn : wellData} /}>Well being</span>
        </li>
      </ul>
    </div>
  </div>
  <div id="footer">
    <div class="button">About amadeus</div>
  </div>
    <div class="popUp">
    {call popup() /}
  </div>
	<div class="mask"></div>
	<div id="loading"></div>
  {/macro}
 {macro popup()}
  <div class="dialog">
    {section {
        id : "popuplist",
        macro : {
          name: "popupmacro"
        }
    }/}
  </div>
  {/macro} 
  {macro popupmacro()}
  {if this.data.result}
  <div class="header">${this.data.result.title}<span class="close"></span></div>
  <div class="content">${this.data.result.desc}</div>
  <div>
  {foreach listdata in this.data.result.list}
    <div class="button">
      <ul>
        <li>${listdata.title}</li>
        <li>${listdata.desc}</li>
        <li class="icon"></li>
      </ul>
    </div>
    {/foreach}
  </div>
  {else/}
  <div class="header"><span class="close"></span></div>
  <div class="content"></div>
  
  {/if}
  {/macro}

{/Template}