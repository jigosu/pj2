
		// hover mainbox
	function hoverparentevent(mainbox){
	let test = document.getElementById(mainbox);
	
	  test.addEventListener("mouseover", function(event){
	  // highlight the mouseover target
	  event.target.parentNode.style.backgroundColor = "#FFFFCC";
	  event.target.parentNode.style.transform="scale(1.1)";
      event.target.parentNode.style.zIndex = 1;
      event.target.parentNode.style.transition = "all 0.5s";	  
     
	}, false);
	
	test.addEventListener("mouseout", function(event){
	  event.target.parentNode.style.backgroundColor = "white";
	  event.target.parentNode.style.transform="scale(1.0)";
      event.target.parentNode.style.zIndex = 1;
      event.target.parentNode.style.transition = "all 0.5s";	  

	}, false);
	}
	