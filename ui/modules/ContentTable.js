import React from 'react';
import NavLink from './NavLink'

class ContentTable extends React.Component {
	constructor() {
		super();

		this.state = {
			data:
			[
				{
					"id":"x",
					"name":"Sidqi",
					"age":"24"
				},

				{
					"id":"y",
					"name":"Tiara",
					"age":"23"
				},

				{
					"id":"a",
					"name":"Intan",
					"age":"1"
				}
			]
		}
	}

	render() {
		return (
			<div>
				<table>
					<tbody>
						{this.state.data.map((lovely, pengulang) => <TableRow key = {pengulang} data = {lovely} />)}
					</tbody>
				</table>
			</div>
		);
	}
}

class TableRow extends React.Component {
   render() {
      return (
         <tr>
            <td>{this.props.data.id}</td>
            <td>{this.props.data.name}</td>
            <td>{this.props.data.age}</td>
         </tr>
      );
   }
}

export default ContentTable;