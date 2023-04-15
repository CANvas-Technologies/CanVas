import React, {useState} from 'react';


function FileUploadPage(){
    const [selectedFile, setSelectedFile] = useState();
    const [isFilePicked, setIsFilePicked] = useState(false);
    /* selectedFile contains info on the currently picked file
        isFilePicked determines if a file has ben picked or not*/
    const changeHandler = (event) => {
        setSelectedFile(event.target.files[0]);
        setIsSelected(true);
    };

    const handleSubmission = () => {};
    return(
    <div>
        <input
            type='file'
            name='file'
            onChange={changeHandler}
        />
        <div>
            <button onClick={handleSubmission}>Submit</button>
        </div>
    </div>



    )



}