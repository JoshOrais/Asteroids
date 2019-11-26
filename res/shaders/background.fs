#version 330 core

in vec3 aColor;
in vec2 texCoords;

uniform float offx;
uniform float offy;
uniform float scale;

out vec4 fragOut;

uniform sampler2D texture_sampler;

void main(){
	
	fragOut = texture(texture_sampler, texCoords * scale + vec2(offx, offy));

}