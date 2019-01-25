orga.VisField = function() {
	orga.VisField.parent.call(this);

	// Exposed to Java
	this.users;
	this.start;
	this.end;

	// Internal
	this.userGroups;
	this.total;

	// Internal for vis.js
	this.groups;
	this.items;
	this.options;
};
scout.inherits(orga.VisField, scout.FormField);

orga.VisField.prototype._render = function() {
	this.addContainer(this.$parent, 'vis');
	let graphDiv = this.$parent.appendDiv('graph2d');
	this.addField(graphDiv);
	this.calculatePropertiesAndGraph();
};

// The following three methods _render{fieldName} are called automatically after the property
// has been set through the JsonVisField.class
orga.VisField.prototype._renderUsers = function(model) {
	this.calculatePropertiesAndGraph();
};

orga.VisField.prototype._renderStart = function(model) {
	this.calculatePropertiesAndGraph();
};

orga.VisField.prototype._renderEnd = function(model) {
	this.calculatePropertiesAndGraph();
};

orga.VisField.prototype.calculatePropertiesAndGraph = function() {
	this.calculateInternals();
	if (this.graph2d) {
		this.graph2d.destroy();
	}
	this.graph2d = new vis.Graph2d(this.$field[0], this.items, this.groups, this.options);
};

orga.VisField.prototype.calculateInternals = function() {
	this.initializeFields();
	this.calculateTotal();
	this.calculateGroups();
	this.calculateItems();
	this.calculateOptions();
};

orga.VisField.prototype.initializeFields = function() {
	if (!this.users) {
		this.users = {};
	}
	this.users['Total'] = [];
	this.total = {};
	this.groups = new vis.DataSet();
	this.userGroups = {};
	this.items = [];
};

orga.VisField.prototype.calculateTotal = function() {
	Object.values(this.users).forEach(items => {
		items.forEach(item => {
			let max = this.total[item.x];
			if (!max) {
				max = 0;

				// Add pseudo item
				this.users['Total'].push({
					x: item.x,
					y: 0
				});
			}
			this.total[item.x] = max + item.y;
		});
	});
};

orga.VisField.prototype.calculateGroups = function() {
	let groupId = 0;
	for (let user in this.users) {
		this.userGroups[user] = groupId;
		this.groups.add({
			id: groupId++,
			content: user,
			options: this.isUserTotal(user) ? {excludeFromLegend: true} : {}
		});
	}
};

orga.VisField.prototype.calculateItems = function() {
	Object.entries(this.users).forEach(([user, items]) => {
		let id = this.userGroups[user];
		items.forEach(item => this.items.push({
			x: item.x,
			y: item.y,
			group: id,
			label: this.calculateTotalLabel(item, user)
		}));
	});
};

orga.VisField.prototype.calculateOptions = function() {
	this.options = {
			style: 'bar',
			stack: true,
			barChart: {
				width: 50,
				align: 'center',
				sideBySide: true
				},
			drawPoints: {
			    style: 'circle'
			      },
			dataAxis: {
				left: {
	                range: {min:0}
	            },
	            right: {
	                range: {min:0}
	            },
				icons: false
			},
			orientation: 'top',
			start: this.start,
			end: this.end,
			legend: true,
			moveable: false,
			autoResize: true
	};
};


orga.VisField.prototype.calculateTotalLabel = function(item, user) {
	return {
		// Use pseudo User 'Total' to display a total at the bottom of the bar
		content: this.isUserTotal(user) ? this.total[item.x] + 'h' : '',
		yOffset: -8 ,
		xOffset: this.offset(item.x)
	};
};

orga.VisField.prototype.isUserTotal = function(user) {
	return user === 'Total';
};

orga.VisField.prototype.offset = function(key) {
	let ret = -6;
	let max = this.total[key];
	if (max >= 10) {
		ret -= 3;
	}
	if (max >= 100) {
		ret -= 3;
	}
	return ret
};
