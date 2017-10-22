$(document).ready(function(){
    $('#myTable').DataTable();
});

function lihatNik(value){
	window.location = "/penduduk?nik=" + value;
}