/*******************************************************************************
 * Copyright 2014 The Trustees of Indiana University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package edu.indiana.d2i.sloan.bean;

public class ImageInfoBean {
	private String imageName;
	private String imageStatus;
	private String imageDescription;
	private String imagePath;
	private String loginUserName;
	private String loginPassWord;

	public ImageInfoBean(String imageName, String imageStatus, String imageDescription) {
		this.imageName = imageName;
		this.imageStatus = imageStatus;
		this.imageDescription = imageDescription;
	}

	public ImageInfoBean(String imageName, String imageStatus, String imageDescription, String imagePath, String loginUserName, String loginPassWord) {
		this.imageName = imageName;
		this.imageStatus = imageStatus;
		this.imageDescription = imageDescription;
		this.imagePath = imagePath;
		this.loginUserName = loginUserName;
		this.loginPassWord = loginPassWord;
	}
	
	public String getImageName() {
		return imageName;
	}
	
	public String getImageDescription() {
		return imageDescription;
	}

	public String getImageStatus() {
		return imageStatus;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setImageDescription(String imageDescription) {
		this.imageDescription = imageDescription;
	}

	public void setImageStatus(String imageStatus) {
		this.imageStatus = imageStatus;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getLoginUserName() {
		return loginUserName;
	}

	public void setLoginUserName(String loginUserName) {
		this.loginUserName = loginUserName;
	}

	public String getLoginPassWord() {
		return loginPassWord;
	}

	public void setLoginPassWord(String loginPassWord) {
		this.loginPassWord = loginPassWord;
	}
}
