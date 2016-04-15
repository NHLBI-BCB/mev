define(["lodash"], function(_){ "use strict";
	function PathwayEnrichmentVM(){
		function factory(scope, project, analysis){
			scope.project = project;
			scope.analysis = analysis;
			scope.selectedRows = {};
			scope.selectedGenes = [];
			scope.headers = [	       
		       {
		           'name': 'Description',
		           'field': "Description",
		           'icon': "search",
		           'nowrap': true,
		           'check': function(value, row){	  	           		
				        scope.selectedGenes = _.uniq(_.flatten(_.map(scope.selectedRows, function(item){
							return item.geneID.split(/[ \/]/).map(function(gene){return {id: gene};});
						})), "id");
			           	console.log("selectedGenes", scope.selectedGenes);
	           		}
		       },{
		           'name': 'P-Value',
		           'field': "pvalue",
		           'icon': "<=",
		           'default': "0.05"
		       },{
		           'name': 'P-Adjust',
		           'field': "p.adjust",
		           'icon': "<="
		       },{
		           'name': 'q-Value',
		           'field': "qvalue",
		           'icon': "<="
		       },{
		           'name': 'Count',
		           'field': "Count",
		           'icon': ["<=", ">="],
		           'datatype': "integer"
		       },{
		           'name': 'ID',
		           'field': "geneID",
		           'icon': "search"
		       },

		  	];
		  	scope.getSelection=function(){
		  		return scope.selectedGenes;
		  	};
			scope.viewGenes = function (filteredResults){
			   	scope.filteredResults = filteredResults;
			  	// scope.applyToHeatmap(filteredResults);
			};
		}				
		factory.$inject=["$scope", "project", "analysis"];
		return factory;
	}	
	PathwayEnrichmentVM.$inject=[];
	PathwayEnrichmentVM.$name="PathwayEnrichmentVMFactoryFactory";
	PathwayEnrichmentVM.$provider="factory";
	return PathwayEnrichmentVM;
});