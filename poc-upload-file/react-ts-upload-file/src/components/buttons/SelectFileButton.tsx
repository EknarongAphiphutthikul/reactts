import React, { FC, useState } from 'react';
import { uploadChunkFile } from '../../api/UploadChunkFileApi';

const SelectFileButton: FC = () => {
  const [file, setFile] = useState<File | null>(null);

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      setFile(e.target.files[0]);
      uploadChunkFile(e.target.files[0], "").then((response) => {
        console.log(response);
      }).catch((error) => {
        console.log(error);
      });
    }
  };

  return (
    <div>
      <input 
        type="file" 
        onChange={handleFileChange} 
      />
      {file && <p>Selected file: {file.name}</p>}
    </div>
  );
}

export default SelectFileButton;