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
        <li class="headerTxt"> Well being travel </li>
        <li>
          <div class="formContainer">
            <div class="formElements where skin skin-square">
              <div><span class="dB">I want to travel</span>
                <input name="where" type="radio" value="" checked id="domestic">
                <label for="domestic">Domestic</label>
                <input name="where" type="radio" value="" id="international">
                <label  for="international">International</label>
              </div>
              <div > <span class="dB select">I am </span>
                <select id="age" name="Age" {on change {fn: changeAge}/}>
                  <option value="4">50 to 60 Years</option>
                  <option value="3">60 to 70 Years</option>
                  <option value="2">70 to 80 Years</option>
                  <option value="1">Above 80 Years</option>
                </select>
              </div>
            </div>
            <div class="selectOptions skin skin-square">
              <div class="title">I am travelling for</div>
			    {section {
			        id : "catlist",
			        macro : {
			          name: "cats"
			        }
			    }/}
              <div class="mConditions">
                <input name="" type="checkbox" value="" id="mCondition">
                <label for="mCondition">I have medical conditions</label>
              </div>
            </div>
          </div>
        </li>
      </ul>
    </div>
  </div>
  <div id="footer">
    <div class="button" {on click {fn: getDetails}/}>Recommend places</div>
  </div>
  <div class="mask"></div>
  <div class="popUp1">
  <div class="header">Tell us more about your health conditions<span class="close2"></span></div>
  <div class="list skin skin-square"> <span>
    <input class="medlist" name="" type="checkbox" value="Diabetic" id="df">
    <label for="df">Diabetic food</label>
    </span> <span>
    <input class="medlist" name="" type="checkbox" value="Wheel chair" id="wheel">
    <label for="wheel">Wheel chair options</label>
    </span> <span>
    <input class="medlist" name="" type="checkbox" value="other requirement 1" id="or1">
    <label for="or1">other requirement 1</label>
    </span> <span>
    <input class="medlist" name="" type="checkbox" value="other requirement 2" id="or2">
    <label for="or2">other requirement 2</label>
    </span>
      <div id="footer">
    <div class="button done">Done</div>
  </div>
    </div>
</div>
  
  {/macro}
 {macro cats()}
 {foreach cat in data.cats.lists}
 {if data.age > cat.type}
  <input class="catlist" name="" type="checkbox" value="${cat.id}" id="${cat.id}">
  <label for="${cat.id}">${cat.title}</label>
  {/if}
{/foreach}
 {/macro} 
  {macro popupmacro()}
   {/macro}

{/Template}