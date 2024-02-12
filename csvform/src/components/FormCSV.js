import React, { useState } from "react";
import './FormCSV.css';
// import axios from "axios";
import * as XLSX from 'xlsx';

function Form() {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });



  const [excelFile, setExcelFile] = useState(null);
  const [typeError, setTypeError] = useState(null);

  const [excelData, setExcelData] = useState(null);


  const [errors, setErrors] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });




const handleFile=(e)=>{
  let fileTypes = ['application/vnd.ms-excel','application/vnd.openxmlformats-officedocument.spreadsheetml.sheet','text/csv'];
  let selectedFile = e.target.files[0];
  if(selectedFile){
    if(selectedFile&&fileTypes.includes(selectedFile.type)){
      setTypeError(null);
      let reader = new FileReader();
      reader.readAsArrayBuffer(selectedFile);
      reader.onload=(e)=>{

        setExcelFile(e.target.result);

        const workbook = XLSX.read(e.target.result, { type: 'buffer' });
        const worksheetName = workbook.SheetNames[0];
        const worksheet = workbook.Sheets[worksheetName];
        const data = XLSX.utils.sheet_to_json(worksheet);
        localStorage.setItem('excelData', JSON.stringify(data.slice(0, 10)));
        // localStorage.setItem('formData',JSON.stringify(data.slice(0, 10)))
        console.log("Data from excel", data)
        console.log("Successfully Data stored in  Local Storage");

        console.log(localStorage.setItem('excelData', JSON.stringify(data.slice(0, 10))));

        const storedData = localStorage.getItem('excelData');
        const dataArray = storedData ? JSON.parse(storedData) : [];
    
        console.log("Data in Array Format", dataArray);
      } 
    }
    else{
      setTypeError('Please select only excel file types');
      setExcelFile(null);
    }
  }
  else{
    console.log('Please select your file');
  }
}

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  const validateForm = () => {
    let valid = true;
    const newErrors = { ...errors };

    const nameRegex = /^[a-zA-Z]+$/;
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!formData.firstName.trim().match(nameRegex)) {
      newErrors.firstName = "First name is not valid";
      valid = false;
    } else {
      newErrors.firstName = "";
    }

    if (!formData.lastName.trim().match(nameRegex)) {
      newErrors.lastName = "Last name is not valid";
      valid = false;
    } else {
      newErrors.lastName = "";
    }

    if (!formData.email.trim().match(emailRegex)) {
      newErrors.email = "Email is not valid";
      valid = false;
    } else {
      newErrors.email = "";
    }

    if (formData.password.length < 8) {
      newErrors.password = "Password must be at least 8 characters";
      valid = false;
    } else {
      newErrors.password = "";
    }

    setErrors(newErrors);

    return valid;
  };

  const handleSubmit = (e) => {
    e.preventDefault();


    
    if (validateForm()) {
      
    // if(excelFile!==null){
    //   const workbook = XLSX.read(excelFile,{type: 'buffer'});
    //   const worksheetName = workbook.SheetNames[0];
    //   const worksheet = workbook.Sheets[worksheetName];
    //   const data = XLSX.utils.sheet_to_json(worksheet);
    //   setExcelData(data.slice(0,10));
      console.log("Form Data:", formData);


    // }
    } else {
      console.log("Form has errors. Please fix them.");
    }

  };



  return (
    <div className="o-form">
    <div className="main">
      <form onSubmit={handleSubmit}>
       <div>
          <label htmlFor="fname">First Name</label>
          <input
            type="text"
            name="firstName"
            id="fname"
            value={formData.firstName}
            onChange={handleChange}
          />
        </div>
        <div className="error">{errors.firstName}</div>

        <div>
          <label htmlFor="lname">Last Name</label>
          <input
            type="text"
            id="lname"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
          />
        </div>
        <div className="error">{errors.lastName}</div>

        <div>
          <label htmlFor="email">Email ID</label>
          <input
            type="email"
            id="email"
            name="email"
            placeholder="Enter the email"
            value={formData.email}
            onChange={handleChange}
          />
        </div>
        <div className="error">{errors.email}</div>

        <div>
          <label htmlFor="pass">Password</label>
          <input
            type="password"
            id="pass"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
        </div>
        <div className="error">{errors.password}</div>  

        <input type="file" className="form-control" accept=".csv" required onChange={handleFile} />
        {/* <button type="submit" className="btn btn-success btn-md">UPLOAD</button> */}
        

        <button type="submit" className="butn">Submit</button>
        </form>
    </div>
    <div className="render-data">
    {typeError&&(
          <div className="alert alert-danger" role="alert">{typeError}</div>
        )}

<div className="viewer">
        {excelData?(
          <div className="table-responsive">
            <table className="table">

              <thead>
                <tr>
                  {Object.keys(excelData[0]).map((key)=>(
                    <th key={key}>{key}</th>
                  ))}
                </tr>
              </thead>

              <tbody>
                {excelData.map((individualExcelData, index)=>(
                  <tr key={index}>
                    {Object.keys(individualExcelData).map((key)=>(
                      <td key={key}>{individualExcelData[key]}</td>
                    ))}
                  </tr>
                ))}
              </tbody>

            </table>
          </div>
        ):(
          <div>No File is uploaded yet!</div>
        )}
      </div>   

    </div>
    </div>
  );
}

export default Form;
