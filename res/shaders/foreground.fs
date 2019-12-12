#version 330 core

in vec3 aColor;
in vec2 texCoords;

out vec4 fragOut;

uniform sampler2D texture_sampler;

void main(){

	fragOut = texture(texture_sampler, texCoords);
	//fragOut = vec4(texCoords, 1.f, 1.f);

}
