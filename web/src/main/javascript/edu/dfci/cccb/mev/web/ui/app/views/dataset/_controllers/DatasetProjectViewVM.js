define(["ng", "lodash"], function(ng, _){
	var DatasetProjectViewVM=function DatasetViewVM($scope, $stateParams, $state, dataset, project, AnalysisEventBus, AnalysisTypes){
		that=this;
		console.debug("DatasetProjectViewVM", dataset, project);
		this.project=project;		
		
		this.getProject=function(){
			return project;
		};
		this.getProjectName=function(){
			return project.name;
		};
				
		this.node={nodeName: "Dataset"};
		console.debug("***dataset", dataset, project);
//		this.annotations=annotations;	
		
		project.generateView({
            viewType:'heatmapView', 
            note: "DatasetProjectViewVM",
            labels:{
                row:{keys:dataset.row.keys}, 
                column:{keys:dataset.column.keys}
            },
            expression:{
                min: dataset.expression.min,
                max: dataset.expression.max,
                avg: dataset.expression.avg,
            }
        });
		
		function isAnalysisInDashbaord(analysis){
			return _.find(project.dataset.dashboardItems, function(item){
				return item.name === analysis.name;
			});
			
		}
		
		$scope.$on("ui:projectTree:nodeSelected", function($event, node){
			that.node=node;			
			
			var params = node.nodeConfig.state.getParams(node);
			if(node.nodeParent && node.nodeParent.nodeConfig){
				ng.extend(params, node.nodeParent.nodeConfig.state.getParams(node.nodeParent));
			}
			
			var targetState = "root"+node.nodeConfig.state.name;
			console.debug("ui:projectTree:nodeSelected $on", $event, node, $state, params, targetState);			
			$state.go(targetState, params);
		});
		
		AnalysisEventBus.onAnalysisSuccess($scope, function(type, name, data){
			dataset.loadAnalyses().then(function(response){
				var analysis = _.find(dataset.analyses, function(analysis){ return analysis.name===name; });
				
				if(!analysis.params)
					analysis.params=data;
				
				console.debug("DatasetProjectViewVM onAnalysisSuccess", type, name, analysis);	
				if(isAnalysisInDashbaord(analysis))
					console.debug("dashbaord: analysis is in dashbaord - Refresh!!");
				else
					$state.go("root.dataset.analysis", {analysisType: AnalysisTypes.reverseLookup[type], analysisId: name});
				
			});			
        });
		AnalysisEventBus.onAnalysisLoadedAll($scope, function(){
			console.debug("DatasetProjectViewVM onAnalysisLoadedAll");
			$scope.$broadcast("ui:projectTree:dataChanged");			
		});
		
		$scope.$on('SeletionAddedEvent', function(event, dimensionType){
      	    dataset.resetSelections(dimensionType);      	        	  
         });
		
		_.forEach(project.dataset.dashboardItems, function(item){
			if(item.launch && 
			!_.find(project.dataset.analyses, function(analysis){return analysis.name === item.launch.analysisName;})){
				var params = _.extend(item.launch, {datasetName: project.dataset.datasetName});
				console.debug("dashbaord: launching", item, params);
				project.dataset.analysis.put(params, {});
			}
		});

	};
	DatasetProjectViewVM.$inject=["$scope", "$stateParams", "$state", "dataset", "project", "AnalysisEventBus", "AnalysisTypes"];
	return DatasetProjectViewVM;
});
