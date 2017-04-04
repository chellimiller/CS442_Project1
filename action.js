window.input; window.querytype;

function cs442p1_print(output) {
	document.getElementById("cs442p1_output").innerHTML = output;
}

function cs442p1_error(messages) {
	var output = "Please fix the following issues:</br>";
	for (i = 0; i < messages.length; i++) {
		output += messages[i] + "<br/>";
	}
	cs442p1_print(output); 
}

function cs442p1_prettyPrint() {
	var t = "<table>";
	var te = "</table>";
	var tr = "<tr>";
	var trn = "</tr><tr>";
	var tre = "</tr>";
	var br = "<br/>";
	var td = "<td>";
	var tdl = '<td id=".cs442p1_tleft">';
	var tdr = '<td id=".cs442p1_tright">';
	var tde = "</td>";
	var tdn = "</td><td>";
	var out1 = "<h1>-Output-</h1>" + t;
	
	var row1 = tr + tdl + "Input:" + tde + tdr + data[0] + "-bit" + br + decbin + tde + tre;
	var row2a = tr + tdl + "Multiplicand - " + tdn + tde + trn;
	var row2b = tdl + "Decimal:" + tde + tdr + data[2] + tde + trn;
	var row2c = tdl + "Binary:" + tde + tdr + data[3] + tde + tre;
	var row3a = tr + tdl + "Multiplier - " + tdn + tde + trn;
	var row3b = tdl + "Decimal:" + tde + tdr + data[4] + tde + trn;
	var row3c = tdl + "Binary:" + tde + tdr + data[5] + tde + trn;
	var row3d = tdl + "Two's Complement:" + tde + tdr + data[6] + tde + tre;
	var row45 = tr + trn + tdl + "Steps - " + tdn + tde;
	var rows15 = out1 + row1 + row2a + row2b + row2c + row3a + row3c + row3d + row45;
	
	var p = 0;
	var row6on = "";
	while (p < steps.length) {
		row6on += trn + tdl + steps[p] + tde + tdr + steps[p + 1] + tde;
		p = p + 2;
	}
	
	var output = rows15 + row6on + tre + te;
	
	cs442p1_print(output);
}   

function cs442p1_verify() {
	
}
function cs442p1_submit() {
	input = document.getElementById("cs442p1_form").elements;
 
	if (cs442p1_verify() == true) {
 		cs442p1_prettyPrint();
	}
}
function cs442p1_reset() {
 document.getElementById("cs442p1_form").reset();
 cs442p1_print("");
}
