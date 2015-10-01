spinning = false;
var defaultBet    =  $( "#slider-range-min" ).slider( "value" );
var defaultLine   =  1;




function playSpinMp3(){
	//if ($('#soundbtn').text() === "Mute" ){
	$("sound#spinmp3").jPlayer("volume", 1);
	//};
};
function stopSpinMp3(){
	$("sound#spinmp3").jPlayer("volume", 0);
};
function playSound(name,sec){
	if ($('#soundbtn').text() === "Mute" ){
	$("sound#"+name+"mp3").jPlayer("destroy");
	$("sound#"+name+"mp3").jPlayer( {
		swfPath: "/secured/mp3",
		volume: 1,
		preload:"auto",
		ready: function () {
			$(this).jPlayer("setMedia", {
		 		mp3: "/secured/mp3/"+name+".m4a"
			}).jPlayer("play",sec);
		}
	});
	};
};
function loadSound(name){
	$("sound#"+name+"mp3").jPlayer( {

		swfPath: "/secured/mp3",
		volume: 1,
		preload:"auto",
		ready: function () {
			$(this).jPlayer("setMedia", {
		 		mp3: "/secured/mp3/"+name+".m4a"
			});
		}
	});
};
function showLine(lineno1,lineno2,lineno3,lineno4,lineno5,lineno6,lineno7,lineno8,lineno9,lineno10,lineno11,lineno12,lineno13,lineno14,lineno15,lineno16,lineno17){
	$('.lines polyline').attr("class", "").hide();
	if (lineno1 > 0) {
		blink('#1');
		$('li[data-wins=' + lineno1 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno1+' </xs>')
	}
	if (lineno2 > 0) {
	    blink('#2');
	    $('li[data-wins=' + lineno2 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno2+' </xs>')
	}
	if (lineno3 > 0) {
	    blink('#3');
		$('li[data-wins=' + lineno3 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno3+' </xs>')
	}
	if (lineno4 > 0) {
	    blink('#4');
	    $('li[data-wins=' + lineno4 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno4+' </xs>')
	}
	if (lineno5 > 0) {
	    blink('#5');
	    $('li[data-wins=' + lineno5 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno5+' </xs>')
	}
	if (lineno6 > 0) {
	    blink('#6');
	    $('li[data-wins=' + lineno6 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno6+' </xs>')
	}
	if (lineno7 > 0) {
	    blink('#7');
	    $('li[data-wins=' + lineno7 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno7+' </xs>')
	}
	if (lineno8 > 0) {
	    blink('#8');
	    $('li[data-wins=' + lineno8 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno8+' </xs>')
	}
	if (lineno9 > 0) {
	    blink('#9');
	    $('li[data-wins=' + lineno9 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno9+' </xs>')
	}
	if (lineno10 > 0) {
	    blink('#10');
	    $('li[data-wins=' + lineno10 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno10+' </xs>')
	}
	if (lineno11 > 0) {
	    blink('#11');
	    $('li[data-wins=' + lineno11 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno11+' </xs>')
	}
	if (lineno12 > 0) {
	    blink('#12');
	    $('li[data-wins=' + lineno12 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno12+' </xs>')
	}
	if (lineno13 > 0) {
	    blink('#13');
	    $('li[data-wins=' + lineno13 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno13+' </xs>')
	}
	if (lineno14 > 0) {
	    blink('#14');
	    $('li[data-wins=' + lineno14 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno14+' </xs>')
	}
	if (lineno15 > 0) {
	    blink('#15');
	    $('li[data-wins=' + lineno15 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno15+' </xs>')
	}
	if (lineno16 > 0) {
	    blink('#16');
	    $('li[data-wins=' + lineno16 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno16+' </xs>')
	}
	if (lineno17 > 0) {
	    blink('#17');
	    $('li[data-wins=' + lineno17 + ']').fadeTo("fast",1).addClass('bgline');
		$('#slotresult').append('<xs>x'+lineno17+' </xs>')
	}
	intTotatlLine = parseInt(lineno1)+parseInt(lineno2)+parseInt(lineno3)+parseInt(lineno4)+parseInt(lineno5)+parseInt(lineno6)+parseInt(lineno7)+parseInt(lineno8)+parseInt(lineno9)+parseInt(lineno10)+parseInt(lineno11)+parseInt(lineno12)+parseInt(lineno13)+parseInt(lineno14)+parseInt(lineno15)+parseInt(lineno16)+parseInt(lineno17);
	$('#slotresult').append('<br/><impt>Total: x'+intTotatlLine+'</impt>')
};
function blink(selector){
	setTimeout(function(){
	$(selector).attr("class", "blink").show();
	}, 100 );	
}
function Currency(){
	$('.currency').each(function(){
		if($(this).html() !=' '){
			$(this).formatCurrency({
				useHtml:true,
				symbol:'',
				roundToDecimalPlace: 0
			});
		}
	})

};

function changeBet(n) {
	defaultBet = n;
  	_this = $("#betslabel");
	_this.val(defaultBet);
	$("#totalbet").text(defaultBet*defaultLine);
	
	var arrWins = [ "200","100","50","30","15","10","8","7","6","5","3","2","1" ];
	jQuery.each(arrWins, function() {
		strWin = $("li[data-wins=" + this + "]").attr('data-wins');
      	$("li[data-wins=" + this + "]").find('.impt').text(defaultBet*strWin);
       	return;
   	});
  	Currency();
}

function changeLine(n) {
	defaultLine = n;
  	_this = $("#lineslabel");
	_this.val(defaultLine);
	$("#totalbet").text(defaultBet*defaultLine);
	
	for (var i=0;i<18;i++){
		if (parseInt($('#'+i).attr('id')) <= defaultLine) {
		    $('#'+i).show();
		}else{
			$('#'+i).hide();
		}
	}
	Currency();
}



function startSlot() {
	$(".btn-spin").hide();
	$(".btn-spin-fake").show();
	$(".btn-cashier").hide();
	$('.sliders').slider('disable');
	$('#slotresult').html('Connecting to server...');
	$('.lines polyline').attr("class", "").hide();
	$('.awards li').removeClass('bgline').fadeTo("fast",0.5);

};
function spinWheels(wheel1,wheel2,wheel3,strStatus,intBank,intEndBank,intX1,intX2,intX3,intX4,intX5,intX6,intX7,intX8,intX9,intX10,intX11,intX12,intX13,intX14,intX15,intX16,intX17) {

		$('#banklabel').html(intBank);
		Currency();
		$('#slotresult').html('Spining...');
		playSpinMp3();
		$('.wheels img').addClass('active');
		spinning = 3;
		$('.wheels ul:eq(0)').css('top', -(wheel1 - 1) * 90 + 'px').addClass('anim');
		$('.wheels ul:eq(1)').css('top', -(wheel2 - 1) * 90 + 'px').addClass('anim');
		$('.wheels ul:eq(2)').css('top', -(wheel3 - 1) * 90 + 'px').addClass('anim');
		setTimeout(function () {
			stopSpin(0, wheel1, strStatus, intEndBank, intX1, intX2, intX3, intX4, intX5, intX6, intX7, intX8, intX9, intX10, intX11, intX12, intX13, intX14, intX15, intX16, intX17);
			playSound('stop1', 0);
		}, 500 + parseInt(2000 * Math.random()));
		setTimeout(function () {
			stopSpin(1, wheel2, strStatus, intEndBank, intX1, intX2, intX3, intX4, intX5, intX6, intX7, intX8, intX9, intX10, intX11, intX12, intX13, intX14, intX15, intX16, intX17);
			playSound('stop2', 0);
		}, 500 + parseInt(2000 * Math.random()));
		setTimeout(function () {
			stopSpin(2, wheel3, strStatus, intEndBank, intX1, intX2, intX3, intX4, intX5, intX6, intX7, intX8, intX9, intX10, intX11, intX12, intX13, intX14, intX15, intX16, intX17);
			playSound('stop3', 0);
		}, 500 + parseInt(2000 * Math.random()));

};
function stopSpin(slot,wheel,strStatus,intEndBank,intX1,intX2,intX3,intX4,intX5,intX6,intX7,intX8,intX9,intX10,intX11,intX12,intX13,intX14,intX15,intX16,intX17) {
	$('.wheels img:eq(' + slot +')').removeClass('active');
	setTimeout(function(){
		$('.wheels ul:eq(' + slot +')').removeClass('anim');
	}, 500 );	
	spinning --;
	if (spinning == 0 ) {
		stopSpinMp3();
		endSpin(strStatus,intEndBank,intX1,intX2,intX3,intX4,intX5,intX6,intX7,intX8,intX9,intX10,intX11,intX12,intX13,intX14,intX15,intX16,intX17);
	}
};
function endSpin(strStatus,intEndBank,intX1,intX2,intX3,intX4,intX5,intX6,intX7,intX8,intX9,intX10,intX11,intX12,intX13,intX14,intX15,intX16,intX17) {
	setTimeout(function(){
		$('#slotresult').html(strStatus);
//	$('#spin, #cashier').removeAttr('disabled');
		$(".btn-spin").show();
		$(".btn-spin-fake").hide();
		$(".btn-cashier").show();
		$('.sliders').slider('enable');
		$('.sliders').slider('enable');

		$('#banklabel').html(intEndBank);
		Currency();
		spinning = false;
		if (intX1 > 0 || intX2 > 0 || intX3 > 0 || intX4 > 0 || intX5 > 0 || intX6 > 0 || intX7 > 0 || intX8 > 0 || intX9 > 0 || intX10 > 0 || intX11 > 0 || intX12 > 0 || intX13 > 0 || intX14 > 0 || intX15 > 0 || intX16 > 0 || intX17 > 0){
			showLine(intX1,intX2,intX3,intX4,intX5,intX6,intX7,intX8,intX9,intX10,intX11,intX12,intX13,intX14,intX15,intX16,intX17);
			playSound('won',0);
		}else{
			$('.awards li').fadeTo("fast",1);
			playSound('lose',0);
		};
	}, 1000);
};

function endForce() {
	playSound('warning',0);
	$('#slotresult').html('You have not enough chips in your bank.');
	$(".btn-spin").show();
	$(".btn-spin-fake").hide();
	$(".btn-cashier").show();
	$('.sliders').slider('enable');
	$('.sliders').slider('enable');
};

function disableSliderTrack($slider){
    $slider.bind("mousedown", function(event){
	    return isTouchInSliderHandle($(this), event);   
	});
	$slider.bind("touchstart", function(event){
		return isTouchInSliderHandle($(this), event.originalEvent.touches[0]);
	});
}
function isTouchInSliderHandle($slider, coords){
	var x = coords.pageX;
    var y = coords.pageY;
    var $handle = $slider.find(".ui-slider-handle");
    var left = $handle.offset().left;
    var right = (left + $handle.outerWidth());
    var top = $handle.offset().top;
    var bottom = (top + $handle.outerHeight());
    return (x >= left && x <= right && y >= top && y <= bottom);    
}