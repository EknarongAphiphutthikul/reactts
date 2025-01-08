import React, { FC, useState } from 'react';
import { uploadChunkFile } from '../../api/UploadChunkFileApi';

const SelectFileButton: FC = () => {
  const [file, setFile] = useState<File | null>(null);

  const handleFileChange =  async(e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files) {
      setFile(e.target.files[0]);
      await uploadChunkFile(e.target.files[0], "").then((response) => {
        console.log("success", response);
      }).catch((error) => {
        console.log("error", error);
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