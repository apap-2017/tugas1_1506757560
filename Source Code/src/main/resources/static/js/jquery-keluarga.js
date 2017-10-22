$(document).ready(function(){
	$('#select-kot').on('change', function() {
		$("#select-kec").val('0');
		$("#select-kel").val('0');
		$('.kec').attr("hidden", true);
		$temp = '.kota-' + this.value;
		$($temp).attr("hidden", false);
		
	});
	$('#select-kec').on('change', function() {   
		$("#select-kel").val('0');
		$('.kel').attr("hidden", true);
		$temp = '.kecamatan-' + this.value;
		$($temp).attr("hidden", false);
	});
});