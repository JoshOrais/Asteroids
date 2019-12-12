#version 330 core

in vec3 aColor;
in vec2 texCoords;

out vec4 fragOut;

uniform sampler2D texture_sampler;
uniform float flash;

void main(){

	vec4 flashy = flash * vec4(1.f, 0.f, 0.f, 0.f);
	vec4 texturey = texture(texture_sampler, texCoords);

	fragOut = flashy + texturey;

}
