import React, { useState } from 'react';
import { uploadFile } from '../services/api';

                                                              

const TaskAttachment = ({ taskId }) => {
  const [selectedFile, setSelectedFile] = useState(null);
  const [uploadError, setUploadError] = useState('');

  const handleFileSelect = (e) => {
    setSelectedFile(e.target.files[0]);
    setUploadError('');
  };

                                                                  

  const handleUpload = async () => {
    try {
      const formData = new FormData();
      formData.append('file', selectedFile);
      formData.append('taskId', taskId);
      
      await uploadFile(formData);
                                              

    } catch (error) {
      console.error('Upload failed:', error);
                                               

    }
  };

  return (
    <div className="attachment-upload">
      <input type="file" onChange={handleFileSelect} />
      {uploadError && <p className="error">{uploadError}</p>}
      <button onClick={handleUpload} disabled={!selectedFile}>
        Upload Attachment
      </button>
    </div>
  );
};

export default TaskAttachment;