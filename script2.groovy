<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bitbucket Users Report</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            padding: 20px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: left;
            cursor: pointer; /* Make header clickable */
        }
        th {
            background-color: #f4f4f4;
        }
        /* Alternate row colors */
        tr:nth-child(odd) {
            background-color: #f9f9f9;
        }
        tr:nth-child(even) {
            background-color: #e9e9e9;
        }
        /* Row count display */
        #rowCount {
            margin-bottom: 10px; /* Margin for space between count and table */
            font-weight: bold;
        }
    </style>
</head>
<body>

<h2>Bitbucket Users Report</h2> <!-- Updated header -->

<!-- Drop-down filter -->
<div class="filter-container">
    <label for="employeeFilter">Filter Employees: </label>
    <select id="employeeFilter">
        <option value="all">All users</option> <!-- Updated option -->
        <option value="service-accounts">Show Service Accounts</option>
        <option value="leavers">Show Ex-Employees Only</option> <!-- Updated option -->
        <option value="employees-only">Show Employees Only</option> <!-- New option -->
    </select>
</div>

<!-- Row Count Display -->
<div id="rowCount">Total Rows: 0</div>

<!-- Table -->
<table id="jsonTable">
    <thead>
        <tr>
            <th>S.No.</th> <!-- Serial Number Column -->
            <th>Name</th>
            <th>Email Address</th>
            <th>Display Name</th>
            <th id="sortDate">Last Authentication Date &#x21C5;</th> <!-- Sort icon placeholder -->
            <th>Current Employee</th>
        </tr>
    </thead>
    <tbody>
        <!-- Data rows will be inserted here -->
    </tbody>
</table>

<script>
    const jsonData = [
        {
            "name": "A123456",
            "emailAddress": "jjsu@ch.com",
            "displayName": "JJ Su",
            "lastAutheticationTimestamp": 1620916996922,
            "lastAuthenticationDate": "2021-09-12 07:33:51",
            "isCurrentEmployee": false
        },
        {
            "name": "B456653",
            "emailAddress": "jjsu@ch.com",
            "displayName": "JJ Su",
            "lastAutheticationTimestamp": 1620916996922,
            "lastAuthenticationDate": "2024-09-12 07:33:51",
            "isCurrentEmployee": true
        },
        {
            "name": "admin-account",
            "emailAddress": "admin@ch.com",
            "displayName": "Admin Account",
            "lastAutheticationTimestamp": 1620916996922,
            "lastAuthenticationDate": "2022-03-11 08:22:00",
            "isCurrentEmployee": false
        },
        {
            "name": "C789012",
            "emailAddress": "cuser@ch.com",
            "displayName": "C User",
            "lastAutheticationTimestamp": 1620916996922,
            "lastAuthenticationDate": "2020-01-15 08:22:00",
            "isCurrentEmployee": false
        },
        {
            "name": "A000000",
            "emailAddress": "service@ch.com",
            "displayName": "Service Account",
            "lastAutheticationTimestamp": 1620916996922,
            "lastAuthenticationDate": "2023-01-10 09:30:00",
            "isCurrentEmployee": false
        },
        {
            "name": "Z987654",
            "emailAddress": "zuser@ch.com",
            "displayName": "Z User",
            "lastAutheticationTimestamp": 1620916996922,
            "lastAuthenticationDate": "2021-07-15 10:30:00",
            "isCurrentEmployee": false
        }
    ];

    const tableBody = document.querySelector("#jsonTable tbody");
    const employeeFilter = document.getElementById("employeeFilter");
    const sortDateHeader = document.getElementById("sortDate");
    const rowCountDisplay = document.getElementById("rowCount");

    let isAscending = false; // Start with descending order (false = descending)

    // Regex to check if the name follows the pattern (an alphabet followed by 6 digits)
    const namePattern = /^[a-zA-Z]\d{6}$/;

    // Function to render the table based on the selected filter
    function renderTable() {
        // Clear existing rows
        tableBody.innerHTML = '';

        // Get current filter
        const filterType = employeeFilter.value;

        // Filter the data based on the selected option
        let filteredData = jsonData;

        if (filterType === 'service-accounts') {
            // Show only service accounts (names not matching the pattern)
            filteredData = filteredData.filter(item => !namePattern.test(item.name));
        } else if (filterType === 'leavers') {
            // Show only ex-employees (Current Employee = No, Name pattern matches)
            filteredData = filteredData.filter(item => 
                !item.isCurrentEmployee && namePattern.test(item.name)
            );
        } else if (filterType === 'employees-only') {
            // Show only employees (Current Employee = Yes, Name pattern matches)
            filteredData = filteredData.filter(item => 
                item.isCurrentEmployee && namePattern.test(item.name)
            );
        }

        // Sort filtered data by Last Authentication Date
        filteredData = sortByDate(filteredData, isAscending);

        // Update row count
        rowCountDisplay.innerText = `Total Rows: ${filteredData.length}`;

        // Render rows with serial number
        filteredData.forEach((item, index) => {
            const row = document.createElement("tr");

            row.innerHTML = `
                <td>${index + 1}</td> <!-- Dynamically add Serial Number -->
                <td>${item.name}</td>
                <td>${item.emailAddress}</td>
                <td>${item.displayName}</td>
                <td>${item.lastAuthenticationDate}</td>
                <td>${item.isCurrentEmployee ? 'Yes' : 'No'}</td>
            `;

            tableBody.appendChild(row);
        });
    }

    // Function to sort the data based on the last authentication date
    function sortByDate(data, ascending) {
        return data.slice().sort((a, b) => {
            const dateA = new Date(a.lastAuthenticationDate).getTime();
            const dateB = new Date(b.lastAuthenticationDate).getTime();
            return ascending ? dateA - dateB : dateB - dateA;
        });
    }

    // Initial rendering of the table with default descending sort
    renderTable();

    // Event listener for the drop-down filter
    employeeFilter.addEventListener("change", function() {
        renderTable(); // Render the table based on selected filter
    });

    // Event listener for sorting by last authentication date
    sortDateHeader.addEventListener("click", function() {
        // Toggle the sorting order
        isAscending = !isAscending;

        // Re-render the table with current filter and sorted data
        renderTable();
    });
</script>

</body>
</html>
