
var project_id = $('#project_id').val();

$('#tblConsultant').DataTable({
	"processing": true,
	"serverSide": true,
	"paging": true,
	//"scrollY": "230px",
	"ajax": {
		"url": "/employee/api/consultant/"+project_id,
		"type": "GET"
	},
	columnDefs: [
		{
			"targets": -1, //last column
			"orderable": false, //set not orderable
		},
	],
	columns: [
		{ "data": "name" },
		//{ "data": "consultant" },
		{
			data: "id", render: function(data, type, row, meta) {
				html = "<div>";
				html += "<a href='/employee/" + data + "/details'><button type='button' class='btn btn-outline-info btn-sm'><i class='fa fa-eye'></i></button></a>";
				html += "</div>";
				return html;
			}
		},
	],
	"lengthMenu": [[4, 8, 16, 24, 100], [4, 8, 16, 24, 100]],
	language: {
		"lengthMenu": "Mostrar _MENU_ itens por página",
		"zeroRecords": "Não foi encontrado nenhum registo",
		"info": "Mostrando página _PAGE_ de _PAGES_",
		"infoEmpty": "Nenhum registo encontrado",
		"infoFiltered": "(fitrados apartir _MAX_ dos registos)",
		"paginate": {
			"first": "Primeiro",
			"last": "Último",
			"next": "Próximo",
			"previous": "Anterior"
		},
		"search": "Pesquisar: "
	}
});


$('#tblConsultant tbody').on('click', '.delete', function() {

	var id = $(this).attr('rel');

	if (confirm('Tem certeza que deseja remover?')) {
		// ajax delete data to database
		$.ajax({
			url: "/project-consultants/api/" + id,
			type: "DELETE",
			dataType: "JSON",
			success: function(data) {
				reload_table();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				var err = JSON.parse(jqXHR.responseText);
				alert(err.Message);
			}
		});
	}
});

function reload_table() {
	$('#tblConsultant').DataTable().destroy();
	fill_table();
}
